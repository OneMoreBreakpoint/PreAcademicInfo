package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.services.IProfessorService;
import data_layer.domain.Enrollment;
import factory.LessonFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import utils.LessonType;
import utils.TestConstants;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProfessorIT extends BaseIntegrationTest {

    @Autowired
    private IProfessorService professorService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
        enrollment.getLessons().get(0).setType(LessonType.LABORATORY); //prof has LAB rights only so he can only write LABS
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

}
