package bussiness_layer.services.impl;

import bussiness_layer.dto.CourseDto;
import bussiness_layer.dto.LessonTemplateDto;
import bussiness_layer.mappers.CourseMapper;
import bussiness_layer.mappers.LessonTemplateMapper;
import bussiness_layer.services.ICourseService;
import bussiness_layer.utils.LessonTemplateDtoValidator;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class CourseService implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private ILessonTemplateRepository lessonTemplateRepository;

    @Autowired
    private IProfessorRightRepository professorRightRepository;

    @Autowired
    private ILessonRepository lessonRepository;

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

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

    private void removeAssociatedLessons(LessonTemplate lessonTemplate) {
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

    private void addAssociatedLessons(LessonTemplate lessonTemplate) {
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

}
