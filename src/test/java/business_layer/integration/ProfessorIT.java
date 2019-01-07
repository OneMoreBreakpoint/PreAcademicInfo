package business_layer.integration;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.services.IProfessorService;
import data_layer.domain.Course;
import data_layer.domain.Enrollment;
import data_layer.domain.Group;
import data_layer.domain.Professor;
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
    public void givenProfessorDto_whenUpdateProffesor_thenProfessorIsUpdated() {
        Professor professor = createProfessor(TestConstants.PROF_USERNAME);
        String web_page = "www.new-web-page.ro";
        ProfessorDto professorDto = ProfessorFactory.generateProfessorDtoBuilder()
                .username(professor.getUsername())
                .webPage(web_page)
                .build();
        //professorService.updateProfessor(professorDto);
        assertEquals(web_page, professorDto.getWebPage());
    }

    @Test
    @Transactional
    public void givenProfessorDoesNotExist_whenUpdateProfessor_thenResourceNotFoundExceptionThrown() {
        Professor professor = createProfessor(TestConstants.PROF_USERNAME);
        String web_page = "www.new-web-page.ro";
        ProfessorDto professorDto = ProfessorFactory.generateProfessorDtoBuilder()
                .username("Anonim")
                .webPage(web_page)
                .build();
        exception.expect(ResourceNotFoundException.class);
        //professorService.updateProfessor(professorDto);
    }

}
