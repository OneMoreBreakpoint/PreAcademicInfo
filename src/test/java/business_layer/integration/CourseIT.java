package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.CourseDto;
import bussiness_layer.dto.LessonTemplateDto;
import bussiness_layer.mappers.CourseMapper;
import bussiness_layer.services.ICourseService;
import data_layer.domain.Course;
import data_layer.domain.Enrollment;
import data_layer.domain.LessonTemplate;
import data_layer.domain.Professor;
import factory.LessonTemplateFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import utils.LessonType;
import utils.TestConstants;
import utils.exceptions.AccessForbiddenException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CourseIT extends BaseIntegrationTest {

    @Autowired
    private ICourseService courseService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Transactional
    public void givenLessonTemplateDtosHaveBeenRemoved_whenUpdateCourse_thenLessonAndLessonTemplatesAreRemoved() {
        //Given
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.LABORATORY, (byte) 10);
        addLessonTemplatesToCourse(enrollment.getCourse(), 1, LessonType.LABORATORY, (byte) 40);
        addLessonTemplatesToCourse(enrollment.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte) 15);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment, 2, LessonType.PARTIAL_EXAM_COURSE);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor) userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        courseDto.getLessonTemplates().removeIf(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.PARTIAL_EXAM_COURSE);

        long nrOfPartialLessonsBefore = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        long nrOfPartialTemplatesBefore = lessonTemplateRepository.findAll().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();

        //When
        courseService.updateCourse(TestConstants.PROF_USERNAME, courseDto);

        //Then
        long nrOfPartialLessonsAfter = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        long nrOfPartialTemplatesAfter = lessonTemplateRepository.findAll().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();

        assertEquals(2, nrOfPartialLessonsBefore);
        assertEquals(0, nrOfPartialLessonsAfter);
        assertEquals(2, nrOfPartialTemplatesBefore);
        assertEquals(0, nrOfPartialTemplatesAfter);
    }

    @Test
    @Transactional
    public void givenLessonTemplateDtosHaveBeenAdded_whenUpdateCourse_thenLessonsAndLessonTemplatesAreAdded() {
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 7, LessonType.LABORATORY, (byte) 10);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor) userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        LessonTemplateDto partial = LessonTemplateFactory.generateLessonTemplateDtoBuilder()
                .type(LessonType.PARTIAL_EXAM_COURSE)
                .weight((byte) 30)
                .build();
        courseDto.getLessonTemplates().add(partial);
        long nrOfPartialLessonsBefore = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        long nrOfPartialTemplatesBefore = lessonTemplateRepository.findAll().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();

        //When
        courseService.updateCourse(TestConstants.PROF_USERNAME, courseDto);

        //Then
        long nrOfPartialLessonsAfter = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        long nrOfPartialTemplatesAfter = lessonTemplateRepository.findAll().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        assertEquals(0, nrOfPartialLessonsBefore);
        assertEquals(1, nrOfPartialLessonsAfter);
        assertEquals(0, nrOfPartialTemplatesBefore);
        assertEquals(1, nrOfPartialTemplatesAfter);
    }

    @Test
    @Transactional
    public void givenLessonTemplateDtosHaveBeenUpdated_whenUpdateCourse_thenLessonTemplatesAreUpdated() {
        //When
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 7, LessonType.LABORATORY, (byte) 10);
        addLessonTemplatesToCourse(enrollment.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte) 15);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment, 2, LessonType.PARTIAL_EXAM_COURSE);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor) userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        List<Integer> laboratoryIds = courseDto.getLessonTemplates().stream()
                .filter(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.LABORATORY)
                .map(LessonTemplateDto::getId)
                .collect(Collectors.toList());
        int idOfUpdated1 = laboratoryIds.get(0), idOfUpdated2 = laboratoryIds.get(1);
        courseDto.getLessonTemplates().forEach(lessonTemplateDto -> {
            if (lessonTemplateDto.getId() == idOfUpdated1) {
                lessonTemplateDto.setWeight((byte) 20);
            } else if (lessonTemplateDto.getId() == idOfUpdated2) {
                lessonTemplateDto.setWeight((byte) 0);
            }
        });

        //When
        courseService.updateCourse(TestConstants.PROF_USERNAME, courseDto);

        //Then
        LessonTemplate lesson1 = lessonTemplateRepository.findById(idOfUpdated1).get();
        LessonTemplate lesson2 = lessonTemplateRepository.findById(idOfUpdated2).get();

        assertEquals((byte) 20, (byte) lesson1.getWeight());
        assertEquals((byte) 0, (byte) lesson2.getWeight());

    }

    @Test
    @Transactional
    public void givenProfDoesNotHaveRightsOverLessonTemplate_whenUpdateCourse_thenAccessForbiddenExceptionIsThrown() {
        //When
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE
                , TestConstants.GROUP_CODE, "1111", "_1111");
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 7, LessonType.LABORATORY, (byte) 10);
        addLessonTemplatesToCourse(enrollment.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte) 15);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment, 2, LessonType.PARTIAL_EXAM_COURSE);

        Enrollment enrollment2 = createEnrollment(TestConstants.USERNAME, TestConstants.COURSE_CODE2
                , TestConstants.GROUP_CODE2, "2222", "_2222");
        addLessonTemplatesToCourse(enrollment2.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment2.getCourse(), 7, LessonType.LABORATORY, (byte) 10);
        addLessonTemplatesToCourse(enrollment2.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte) 15);
        addLessonsToEnrollment(enrollment2, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment2, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment2, 2, LessonType.PARTIAL_EXAM_COURSE);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor) userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        int idOfLessonTemplateToHack = enrollment2.getCourse().getLessonTemplates().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.LABORATORY)
                .findAny().get().getId();
        courseDto.getLessonTemplates().stream()
                .filter(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.LABORATORY)
                .findAny().get().setId(idOfLessonTemplateToHack);

        //Then
        exception.expect(AccessForbiddenException.class);

        //When
        courseService.updateCourse(TestConstants.PROF_USERNAME, courseDto);
    }

    @Test
    @Transactional
    public void givenProfessorHasRights_whenGetCourse_thenCourseIsRetrieved() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        CourseDto courseDto = courseService.getCourse(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE);
        //Then
        assertEquals(TestConstants.COURSE_CODE, courseDto.getCode());
    }

    @Test
    public void givenProfessorDoesNotHaveRights_whenGetCourse_thenAccessForbiddenExceptionIsThrown() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(AccessForbiddenException.class);
        //When
        courseService.getCourse(TestConstants.USERNAME, TestConstants.COURSE_CODE);
    }

}
