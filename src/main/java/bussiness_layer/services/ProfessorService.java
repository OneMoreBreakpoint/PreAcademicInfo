package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.mappers.EnrollmentMapper;
import bussiness_layer.mappers.GroupMapper;
import bussiness_layer.mappers.ProfessorRightMapper;
import bussiness_layer.utils.LessonDtoValidator;
import data_layer.domain.Enrollment;
import data_layer.domain.Lesson;
import data_layer.domain.ProfessorRight;
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
    private ICourseRepository courseRepository;


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
                    profUsername, courseCode, groupCode, lesson.getType(), RightType.WRITE)
                    .orElseThrow(AccessForbiddenException::new);
            //update only the fields that matter
            if (lesson.getType() == LessonType.SEMINAR || lesson.getType() == LessonType.LABORATORY) {
                lesson.setBonus(lessonDto.getBonus());
            }
            lesson.setAttended(lessonDto.isAttended());
            lesson.setGrade(lessonDto.getGrade());
        });
        lessonRepository.flush();
    }

    private void stripEnrollmentsOfUnauthorizedLessons(List<EnrollmentDto> enrollmentDtos, List<ProfessorRight> rights) {
        enrollmentDtos.forEach(enrollmentDto -> {
            List<LessonDto> filteredLessons = enrollmentDto.getLessons().stream()
                    .filter(lessonDto -> {
                        boolean crtProfCanReadLesson = rights.stream()
                                .anyMatch(right -> right.getLessonType() == lessonDto.getType() && right.getRightType() == RightType.READ);
                        boolean crtProfCanWriteLesson = rights.stream()
                                .anyMatch(right -> right.getLessonType() == lessonDto.getType() && right.getRightType() == RightType.WRITE);
                        if (crtProfCanReadLesson) {
                            lessonDto.setRightType(RightType.READ);
                        }
                        if (crtProfCanWriteLesson) {
                            lessonDto.setRightType(RightType.WRITE); //set to higher right
                        }
                        return crtProfCanReadLesson;
                    })
                    .collect(Collectors.toList());
            enrollmentDto.setLessons(filteredLessons);
        });
    }

}
