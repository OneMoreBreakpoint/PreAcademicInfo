package bussiness_layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bussiness_layer.dto.EmailNotificationDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.mappers.CourseMapper;
import bussiness_layer.mappers.GroupMapper;
import bussiness_layer.mappers.LessonMapper;
import bussiness_layer.mappers.ProfessorCourseMapper;
import bussiness_layer.mappers.ProfessorRightMapper;
import bussiness_layer.services.IProfessorService;
import bussiness_layer.utils.LessonDtoValidator;
import data_layer.domain.Course;
import data_layer.domain.Group;
import data_layer.domain.Lesson;
import data_layer.domain.Professor;
import data_layer.domain.ProfessorRight;
import data_layer.repositories.ICourseRepository;
import data_layer.repositories.IGroupRepository;
import data_layer.repositories.ILessonRepository;
import data_layer.repositories.IProfessorRepository;
import data_layer.repositories.IProfessorRightRepository;
import utils.LessonType;
import utils.RightType;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class ProfessorService implements IProfessorService {

    @Autowired
    private IProfessorRightRepository professorRightRepository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private ILessonRepository lessonRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IProfessorRepository professorRepository;

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
    @Transactional
    public List<EmailNotificationDto> updateLessons(String profUsername, List<LessonDto> lessonDtos) {
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

        return lessonDtos.stream()
                .map(lessonDto -> lessonRepository.findById(lessonDto.getId()).orElseThrow(ResourceNotFoundException::new))
                .filter(lesson -> lesson.getEnrollment().getStudent().isNotifiedByEmail())
                .map(lesson -> LessonMapper.toEmailNotificationDto(
                        lesson, lesson.getEnrollment().getStudent().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateProfessor(ProfessorDto professorDto, String profUsername) {
        Professor professor = professorRepository.findByUsername(profUsername)
                .orElseThrow(ResourceNotFoundException::new);
        professor.setWebPage(professorDto.getWebPage());
        professor.setProfilePhoto(professorDto.getProfilePhoto());
        professorRepository.flush();
    }

    @Override
    public List<ProfessorCourseDto> getRelatedCourses(String profUsername) {
        List<ProfessorRight> professorRights = professorRightRepository.findByProfessor(profUsername);

        List<Course> courses = CourseMapper.toCoursesList(professorRights);

        List<ProfessorCourseDto> professorCourseDtos = new ArrayList<>();

        courses.forEach(course -> {
            List<Group> groups = professorRightRepository.findByProfessorAndCourse(profUsername, course.getCode()).stream()
                    .map(ProfessorRight::getGroup)
                    .distinct()
                    .collect(Collectors.toList());
            boolean profIsCoordinator = course.getCoordinator().getUsername().equals(profUsername);
            professorCourseDtos.add(ProfessorCourseMapper.toDto(course, groups, profIsCoordinator));
        });

        return professorCourseDtos;
    }

}