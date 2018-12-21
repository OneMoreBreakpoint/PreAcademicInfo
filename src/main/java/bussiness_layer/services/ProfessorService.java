package bussiness_layer.services;

import bussiness_layer.dto.*;
import bussiness_layer.handlers.ProfessorHandler;
import bussiness_layer.mappers.*;
import bussiness_layer.utils.LessonDtoValidator;
import bussiness_layer.utils.LessonTemplateDtoValidator;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.LessonType;
import utils.RightType;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService implements IProfessorService {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Autowired
    private IProfessorRightRepository professorRightRepository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private ILessonRepository lessonRepository;

    @Autowired
    private ILessonTemplateRepository lessonTemplateRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private ProfessorHandler professorHandler;

    @Override
    public List<EnrollmentDto> getEnrollments(String profUsername, String courseCode, String groupCode) {
        groupRepository.findByCode(groupCode).orElseThrow(ResourceNotFoundException::new);
        courseRepository.findByCode(courseCode).orElseThrow(ResourceNotFoundException::new);
        List<ProfessorRight> rights = professorRightRepository.findByProfessorAndCourseAndGroup(profUsername, courseCode, groupCode);
        if (rights.size() == 0) {
            throw new AccessForbiddenException();
        }
        List<Enrollment> enrollments = enrollmentRepository.findByCourseAndGroup(courseCode, groupCode);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }

        List<EnrollmentDto> enrollmentDTOS = EnrollmentMapper.toDtoList(enrollments);
        stripEnrollmentsOfUnauthorizedLessons(enrollmentDTOS, rights);
        return enrollmentDTOS;
    }


    @Override
    public List<ProfessorRightDto> getProfessorRights(String profUsername, String courseCode, String groupCode) {
        return ProfessorRightMapper.toDtoList(
                professorRightRepository.findByProfessorAndCourseAndGroup(profUsername, courseCode, groupCode));
    }

    @Override
    public List<GroupDto> getGroups(String profUsername, String courseCode) {
        courseRepository.findByCode(courseCode).orElseThrow(ResourceNotFoundException::new);
        return GroupMapper.toDtoList(groupRepository.findByProfessorAndCourse(profUsername, courseCode));
    }

    @Override
    public void updateLessons(String profUsername, List<LessonDto> lessonDtos) {
        lessonDtos.forEach(lessonDto -> {
            //check if request is processable
            LessonDtoValidator.validate(lessonDto);
            //check if lesson exist
            Lesson lesson = lessonRepository.findById(lessonDto.getId()).orElseThrow(ResourceNotFoundException::new);
            String courseCode = lesson.getEnrollment().getCourse().getCode();
            String groupCode = lesson.getEnrollment().getStudent().getGroup().getCode();
            //check if crt prof has right to modify this lesson
            professorRightRepository.findByProfessorAndCourseAndGroupAndLessonTypeAndRightType(
                    profUsername, courseCode, groupCode, lesson.getTemplate().getType(), RightType.WRITE)
                    .orElseThrow(AccessForbiddenException::new);
            //update only the fields that matter
            if (lesson.getTemplate().getType() == LessonType.SEMINAR || lesson.getTemplate().getType() == LessonType.LABORATORY) {
                lesson.setBonus(lessonDto.getBonus());
            }
            lesson.setAttended(lessonDto.isAttended());
            lesson.setGrade(lessonDto.getGrade());
        });
        lessonRepository.flush();
    }

    @Override
    public List<ProfessorCourseDto> getRelatedCourses(String profUsername) {
        List<ProfessorRight> professorRights = professorRightRepository.findByProfessor(profUsername);

        return professorHandler.getProfessorCourseDtoList(profUsername, professorRights);
    }

    @Override
    public void updateCourse(String profUsername, CourseDto courseDto) {
        Course course = courseRepository.findByCode(courseDto.getCode())
                .orElseThrow(ResourceNotFoundException::new);
        if (!course.getCoordinator().getUsername().equals(profUsername)) {
            throw new AccessForbiddenException();
        }
        LessonTemplateDtoValidator.validate(courseDto.getLessonTemplates());
        List<LessonTemplate> lessonTemplates = lessonTemplateRepository.findByCourse(course.getCode());
        lessonTemplates.forEach(lessonTemplate -> {
            boolean lessonTemplateMustBeRemoved = courseDto.getLessonTemplates().stream()
                    .noneMatch(lessonTemplateDto ->
                            lessonTemplateDto.getId() != null && lessonTemplateDto.getId().equals(lessonTemplate.getId()));
            if (lessonTemplateMustBeRemoved) {
                if (!lessonTemplate.isOfTypePartial()) {
                    throw new AccessForbiddenException(); //only partials can be removed
                }
                removeAssociatedLessons(lessonTemplate);
                removeLessonTemplate(lessonTemplate);
            }
        });
        courseDto.getLessonTemplates().forEach(lessonTemplateDto -> {
            if (lessonTemplateDto.getId() == null) {
                LessonTemplate lessonTemplate = addLessonTemplate(course, lessonTemplateDto);
                addAssociatedLessons(lessonTemplate);
            } else {
                updateLessonTemplate(course, lessonTemplateDto);
            }
        });
    }

    @Override
    public CourseDto getCourse(String profUsername, String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(ResourceNotFoundException::new);
        List<ProfessorRight> professorRights = professorRightRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (professorRights.size() == 0) {
            throw new AccessForbiddenException();
        }
        return CourseMapper.toDto(course);
    }

    private void removeLessonTemplate(LessonTemplate lessonTemplate) {
        lessonTemplateRepository.delete(lessonTemplate);
        lessonTemplateRepository.flush();
    }

    private void removeAssociatedLessons(LessonTemplate lessonTemplate){
        lessonRepository.deleteByLessonTemplate(lessonTemplate.getId());
        lessonRepository.flush();
    }

    private LessonTemplate addLessonTemplate(Course course, LessonTemplateDto lessonTemplateDto) {
        LessonTemplate lessonTemplate = LessonTemplateMapper.toEntity(lessonTemplateDto);
        if (!lessonTemplate.isOfTypePartial()) {
            throw new AccessForbiddenException(); //only partials can be added
        }
        course.getLessonTemplates().add(lessonTemplate);
        lessonTemplate.setCourse(course);
        LessonTemplate addedLessonTemplate = lessonTemplateRepository.save(lessonTemplate);
        lessonTemplateRepository.flush();
        return addedLessonTemplate;
    }

    private void addAssociatedLessons(LessonTemplate lessonTemplate){
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(lessonTemplate.getCourse().getCode());
        enrollments.forEach(enrollment -> {
            Lesson lesson = Lesson.builder()
                    .template(lessonTemplate)
                    .enrollment(enrollment)
                    .build();
            enrollment.getLessons().add(lesson);
            lessonRepository.save(lesson);
        });
        lessonRepository.flush();
    }

    private void updateLessonTemplate(Course course, LessonTemplateDto lessonTemplateDto) {
        LessonTemplate lessonTemplate = lessonTemplateRepository.findById(lessonTemplateDto.getId())
                .orElseThrow(ResourceNotFoundException::new);
        if (lessonTemplate.getCourse() != course) {
            throw new AccessForbiddenException();
        }
        lessonTemplate.setWeight(lessonTemplateDto.getWeight());
        lessonTemplateRepository.flush();
    }


    private void stripEnrollmentsOfUnauthorizedLessons(List<EnrollmentDto> enrollmentDtos, List<ProfessorRight> rights) {
        enrollmentDtos.forEach(enrollmentDto -> {
            List<LessonDto> filteredLessons = enrollmentDto.getLessons().stream()
                    .filter(lessonDto -> {
                        boolean crtProfCanReadLesson = rights.stream()
                                .anyMatch(right -> right.getLessonType() == lessonDto.getTemplate().getType() && right.getRightType() == RightType.READ);
                        boolean crtProfCanWriteLesson = rights.stream()
                                .anyMatch(right -> right.getLessonType() == lessonDto.getTemplate().getType() && right.getRightType() == RightType.WRITE);
                        if (crtProfCanReadLesson) {
                            lessonDto.getTemplate().setRightType(RightType.READ);
                        }
                        if (crtProfCanWriteLesson) {
                            lessonDto.getTemplate().setRightType(RightType.WRITE); //set to higher right
                        }
                        return crtProfCanReadLesson;
                    })
                    .collect(Collectors.toList());
            enrollmentDto.setLessons(filteredLessons);
        });
    }

}