package business_layer.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import bussiness_layer.dto.*;
import bussiness_layer.mappers.CourseMapper;
import bussiness_layer.mappers.LessonTemplateMapper;
import bussiness_layer.mappers.ProfessorMapper;
import data_layer.domain.*;
import factory.CourseFactory;
import factory.LessonTemplateFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import business_layer.BaseIntegrationTest;
import bussiness_layer.services.IProfessorService;
import factory.LessonFactory;
import factory.ProfessorFactory;
import utils.LessonType;
import utils.RightType;
import utils.TestConstants;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProfessorIT extends BaseIntegrationTest {

    @Autowired
    private IProfessorService professorService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void emptyDatabase(){
        lessonRepository.deleteAll();
        lessonRepository.flush();
        enrollmentRepository.deleteAll();
        enrollmentRepository.flush();
        lessonTemplateRepository.deleteAll();
        lessonTemplateRepository.flush();
        professorRightRepository.deleteAll();
        professorRightRepository.flush();
        courseRepository.deleteAll();
        courseRepository.flush();
        userRepository.deleteAll();
        userRepository.flush();
        groupRepository.deleteAll();
        groupRepository.flush();
    }

    @Test
    @Transactional
    public void givenProfessorHasRights_whenGetEnrollments_thenEnrollmentsRetrieved() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        List<EnrollmentDto> enrollmentDtoList = professorService.getEnrollments(
                TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        assertNotNull(enrollmentDtoList);
        assertEquals(1, enrollmentDtoList.size());
    }

    @Test
    @Transactional
    public void givenCourseDoesNotExist_whenGetEnrollments_thenResourceNotFoundExceptionThrown() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        List<EnrollmentDto> enrollmentDtoList = professorService.getEnrollments(
                TestConstants.PROF_USERNAME, "__", TestConstants.GROUP_CODE);
    }

    @Test
    @Transactional
    public void givenProfessorTeachesAtCourse_whenGetProfessorRights_thenProfessorRightsRetrieved() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        List<ProfessorRightDto> rights = professorService.getProfessorRights(
                TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        assertNotNull(rights);
        assertTrue(rights.size() > 0);
    }

    @Test
    @Transactional
    public void givenProfessorDoesNotTeachAtCourse_whenGetProfessorRight_thenNoProfessorRightsRetrieved() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        List<ProfessorRightDto> rights = professorService.getProfessorRights(
                TestConstants.PROF_USERNAME, "__", TestConstants.GROUP_CODE);
        //Then
        assertNotNull(rights);
        assertEquals(0, rights.size());
    }

    @Test
    @Transactional
    public void givenProfessorHasRights_whenUpdateLessons_thenLessonsAreUpdated() {
        //Given
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        enrollment.getLessons().get(0).getTemplate().setType(LessonType.LABORATORY); //prof has LAB rights only so he can only write LABS
        int lessonId = enrollment.getLessons().get(0).getId();
        boolean attended = true;
        byte bonus = 2, grade = 10;
        LessonDto lessonDto = LessonFactory.generateLessonDtoBuilder()
                .id(lessonId)
                .attended(attended)
                .bonus(bonus)
                .grade(grade)
                .build();
        //When
        professorService.updateLessons(TestConstants.PROF_USERNAME, Arrays.asList(lessonDto));
        //Then
        assertEquals(attended, enrollment.getLessons().get(0).isAttended());
        assertEquals(bonus, (byte) enrollment.getLessons().get(0).getBonus());
        assertEquals(grade, (byte) enrollment.getLessons().get(0).getGrade());
    }

    @Test
    @Transactional
    public void givenProfessorDoesNotHaveRights_whenUpdateLessons_thenAccessForbiddenExceptionThrown() {
        //Given
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //prof has LAB rights only so he can only write LABS, but lesson is of type SEM
        int lessonId = enrollment.getLessons().get(0).getId();
        boolean attended = true;
        byte bonus = 2;
        LessonDto lessonDto = LessonFactory.generateLessonDtoBuilder()
                .id(lessonId)
                .attended(attended)
                .bonus(bonus)
                .build();
        //Then
        exception.expect(AccessForbiddenException.class);
        //When
        professorService.updateLessons(TestConstants.PROF_USERNAME, Arrays.asList(lessonDto));
    }

    @Test
    @Transactional
    public void givenLessonsDoesNotExist_whenUpdateLesson_thenResourceNotFoundExceptionThrown() {
        //Given
        LessonDto inexistentLessonDto = LessonFactory.generateLessonDtoBuilder()
                .id(-1)
                .build();
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        professorService.updateLessons(TestConstants.PROF_USERNAME, Arrays.asList(inexistentLessonDto));
    }

    @Test
    public void givenProfessorWithoutCoursesTaught_whenGetRelatedCourses_thenNullListIsReturned() {
        //Given
        String profUsername = createUser(ProfessorFactory.generateProfessor()).getUsername();

        //When
        List<ProfessorCourseDto> professorCourseDtos = professorService.getRelatedCourses(profUsername);

        //Then
        assertEquals(professorCourseDtos.size(), 0);

    }

    @Ignore
    @Test
    public void givenProfessorWithTwoCoursesTaught_whenGetRelatedCourses_thenProfessorCourseDtoListIsReturned() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createEnrollment(TestConstants.PROF_USERNAME + "abc", TestConstants.COURSE_CODE + 1, TestConstants.GROUP_CODE + 1);

        Group group = groupRepository.findByCode(TestConstants.GROUP_CODE).get();
        Course course = courseRepository.findAll().get(courseRepository.findAll().size() - 1);
        Course course2 = courseRepository.findAll().get(courseRepository.findAll().size() - 2);
        Professor professor = (Professor) userRepository.findByUsername(TestConstants.PROF_USERNAME).get();

        createProfessorRight(professor, course, group, LessonType.LABORATORY, RightType.READ);
        createProfessorRights(professor.getUsername(), course2.getCode(), group.getCode());

        //When
        List<ProfessorCourseDto> professorCourseDtos = professorService.getRelatedCourses(TestConstants.PROF_USERNAME);

        //Then
        assertEquals(professorCourseDtos.size(), 2);
    }

    @Test
    @Transactional
    public void givenLessonTemplateDtosHaveBeenRemoved_whenUpdateCourse_thenLessonAndLessonTemplatesAreRemoved(){
        //Given
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.LABORATORY, (byte)10);
        addLessonTemplatesToCourse(enrollment.getCourse(), 1, LessonType.LABORATORY, (byte)40);
        addLessonTemplatesToCourse(enrollment.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte)15);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment, 2, LessonType.PARTIAL_EXAM_COURSE);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor)userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        courseDto.getLessonTemplates().removeIf(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.PARTIAL_EXAM_COURSE);

        long nrOfPartialLessonsBefore = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        long nrOfPartialTemplatesBefore = lessonTemplateRepository.findAll().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        
        //When
        professorService.updateCourse(TestConstants.PROF_USERNAME, courseDto);

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
    public void givenLessonTemplateDtosHaveBeenAdded_whenUpdateCourse_thenLessonsAndLessonTemplatesAreAdded(){
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 7, LessonType.LABORATORY, (byte)10);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor)userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        LessonTemplateDto partial = LessonTemplateFactory.generateLessonTemplateDtoBuilder()
                .type(LessonType.PARTIAL_EXAM_COURSE)
                .weight((byte)30)
                .build();
        courseDto.getLessonTemplates().add(partial);
        long nrOfPartialLessonsBefore = lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();
        long nrOfPartialTemplatesBefore = lessonTemplateRepository.findAll().stream()
                .filter(lessonTemplate -> lessonTemplate.getType() == LessonType.PARTIAL_EXAM_COURSE)
                .count();

        //When
        professorService.updateCourse(TestConstants.PROF_USERNAME, courseDto);

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
    public void givenLessonTemplateDtosHaveBeenUpdated_whenUpdateCourse_thenLessonTemplatesAreUpdated(){
        //When
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 7, LessonType.LABORATORY, (byte)10);
        addLessonTemplatesToCourse(enrollment.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte)15);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment, 2, LessonType.PARTIAL_EXAM_COURSE);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor)userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

        CourseDto courseDto = CourseMapper.toDto(course);
        List<Integer> laboratoryIds = courseDto.getLessonTemplates().stream()
                .filter(lessonTemplateDto -> lessonTemplateDto.getType() == LessonType.LABORATORY)
                .map(LessonTemplateDto::getId)
                .collect(Collectors.toList());
        int idOfUpdated1 = laboratoryIds.get(0), idOfUpdated2 = laboratoryIds.get(1);
        courseDto.getLessonTemplates().forEach(lessonTemplateDto -> {
            if(lessonTemplateDto.getId() == idOfUpdated1){
                lessonTemplateDto.setWeight((byte)20);
            }else if(lessonTemplateDto.getId() == idOfUpdated2){
                lessonTemplateDto.setWeight((byte)0);
            }
        });

        //When
        professorService.updateCourse(TestConstants.PROF_USERNAME, courseDto);

        //Then
        LessonTemplate lesson1 = lessonTemplateRepository.findById(idOfUpdated1).get();
        LessonTemplate lesson2 = lessonTemplateRepository.findById(idOfUpdated2).get();

        assertEquals((byte)20, (byte)lesson1.getWeight());
        assertEquals((byte)0, (byte)lesson2.getWeight());

    }

    @Test
    @Transactional
    public void givenProfDoesNotHaveRightsOverLessonTemplate_whenUpdateCourse_thenAccessForbiddenExceptionIsThrown(){
        //When
        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE
                , TestConstants.GROUP_CODE, "1111", "_1111");
        addLessonTemplatesToCourse(enrollment.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment.getCourse(), 7, LessonType.LABORATORY, (byte)10);
        addLessonTemplatesToCourse(enrollment.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte)15);
        addLessonsToEnrollment(enrollment, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment, 2, LessonType.PARTIAL_EXAM_COURSE);

        Enrollment enrollment2 = createEnrollment(TestConstants.USERNAME, TestConstants.COURSE_CODE2
                , TestConstants.GROUP_CODE2, "2222", "_2222");
        addLessonTemplatesToCourse(enrollment2.getCourse(), 6, LessonType.SEMINAR, null);
        addLessonTemplatesToCourse(enrollment2.getCourse(), 7, LessonType.LABORATORY, (byte)10);
        addLessonTemplatesToCourse(enrollment2.getCourse(), 2, LessonType.PARTIAL_EXAM_COURSE, (byte)15);
        addLessonsToEnrollment(enrollment2, 6, LessonType.SEMINAR);
        addLessonsToEnrollment(enrollment2, 7, LessonType.LABORATORY);
        addLessonsToEnrollment(enrollment2, 2, LessonType.PARTIAL_EXAM_COURSE);

        Course course = courseRepository.findByCode(TestConstants.COURSE_CODE).get();
        course.setCoordinator((Professor)userRepository.findByUsername(TestConstants.PROF_USERNAME).get());

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
        professorService.updateCourse(TestConstants.PROF_USERNAME, courseDto);
    }

    @Test
    @Transactional
    public void givenProfessorHasRights_whenGetCourse_thenCourseIsRetrieved(){
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        CourseDto courseDto = professorService.getCourse(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE);
        //Then
        assertEquals(TestConstants.COURSE_CODE, courseDto.getCode());
    }

    @Test
    @Transactional
    public void givenProfessorDoesNotHaveRights_whenGetCourse_thenAccessForbiddenExceptionIsThrown(){
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(AccessForbiddenException.class);
        //When
        professorService.getCourse(TestConstants.USERNAME, TestConstants.COURSE_CODE);
    }
}
