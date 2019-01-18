package business_layer.integration;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EmailNotificationDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.LessonTemplateDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.services.IProfessorService;
import data_layer.domain.Course;
import data_layer.domain.Enrollment;
import data_layer.domain.Group;
import data_layer.domain.Lesson;
import data_layer.domain.Professor;
import data_layer.domain.Student;
import factory.LessonFactory;
import factory.ProfessorFactory;
import factory.StudentFactory;
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

        Student student = StudentFactory.generateStudentBuilder()
                .username(TestConstants.STUD_USERNAME)
                .notifiedByEmail(false)
                .build();
        createUser(student);

        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE,
                TestConstants.PROF_COORDINATOR, TestConstants.STUD_USERNAME);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        enrollment.getLessons().get(0).getTemplate().setType(LessonType.LABORATORY); //prof has LAB rights only so he can only write LABS

        Lesson lesson = enrollment.getLessons().get(0);
        int lessonId = lesson.getId();
        boolean attended = true;
        byte bonus = 2, grade = 10;
        LessonDto lessonDto = LessonFactory.generateLessonDtoBuilder()
                .id(lessonId)
                .attended(attended)
                .bonus(bonus)
                .grade(grade)
                .template(LessonTemplateDto.builder()
                        .type(lesson.getTemplate().getType())
                        .nr(lesson.getTemplate().getNr())
                        .build())
                .build();
        //When
        List<EmailNotificationDto> emailNotificationDtos = professorService.updateLessons(
                TestConstants.PROF_USERNAME, Arrays.asList(lessonDto));

        //Then
        assertEquals(emailNotificationDtos.size(), 0); //student doesn't want to be notified

    }

    @Test
    @Transactional
    public void givenProfessorHasRights_whenUpdateLessons_thenLessonsAreUpdatedAndNotNullEmailNotificationDtoIsReturned() {
        //Given

        Student student = StudentFactory.generateStudentBuilder()
                .username(TestConstants.STUD_USERNAME)
                .notifiedByEmail(true)
                .build();
        createUser(student);

        Enrollment enrollment = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE,
                TestConstants.PROF_COORDINATOR, TestConstants.STUD_USERNAME);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        enrollment.getLessons().get(0).getTemplate().setType(LessonType.LABORATORY); //prof has LAB rights only so he can only write LABS

        Lesson lesson = enrollment.getLessons().get(0);
        int lessonId = lesson.getId();
        boolean attended = true;
        byte bonus = 2, grade = 10;
        LessonDto lessonDto = LessonFactory.generateLessonDtoBuilder()
                .id(lessonId)
                .attended(attended)
                .bonus(bonus)
                .grade(grade)
                .template(LessonTemplateDto.builder()
                        .type(lesson.getTemplate().getType())
                        .nr(lesson.getTemplate().getNr())
                        .build())
                .build();
        //When
        List<EmailNotificationDto> emailNotificationDtos = professorService.updateLessons(
                TestConstants.PROF_USERNAME, Arrays.asList(lessonDto));

        //Then
        assertEquals(emailNotificationDtos.size(), 1); //student want to be notified

        EmailNotificationDto emailNotificationDto = emailNotificationDtos.get(0);
        Lesson updatedLesson = enrollment.getLessons().get(0);

        assertEquals(emailNotificationDto.isAttended(), updatedLesson.isAttended());
        assertEquals(emailNotificationDto.getBonus(), updatedLesson.getBonus());
        assertEquals(emailNotificationDto.getGrade(), updatedLesson.getGrade());
        assertEquals(emailNotificationDto.getCourseName(), updatedLesson.getTemplate().getCourse().getName());
        assertEquals(emailNotificationDto.getLessonType(), updatedLesson.getTemplate().getType());
        assertEquals(emailNotificationDto.getLessonNumber(), updatedLesson.getTemplate().getNr());
        assertEquals(emailNotificationDto.getEmailAddress(), updatedLesson.getEnrollment().getStudent().getEmail());

    }

    @Test
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

    @Test
    @Transactional
    public void givenProfessorWithTwoCoursesTaught_whenGetRelatedCourses_thenProfessorCourseDtoListIsReturned() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE,
                TestConstants.PROF_COORDINATOR, TestConstants.STUD_USERNAME);
        createEnrollment(TestConstants.PROF_USERNAME2, TestConstants.COURSE_CODE2, TestConstants.GROUP_CODE2,
                TestConstants.PROF_COORDINATOR, TestConstants.STUD_USERNAME2);

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
        //Given
        Professor professor = createProfessor(TestConstants.PROF_USERNAME);
        String web_page = "www.new-web-page.ro";
        ProfessorDto professorDto = ProfessorFactory.generateProfessorDtoBuilder()
                .username(professor.getUsername())
                .webPage(web_page)
                .build();
        //When
        professorService.updateProfessor(professorDto, TestConstants.PROF_USERNAME);
        //Then
        assertEquals(web_page, professorDto.getWebPage());
    }

    @Test
    public void givenProfessorDoesNotExist_whenUpdateProfessor_thenResourceNotFoundExceptionThrown() {
        //Given
        Professor professor = createProfessor(TestConstants.PROF_USERNAME);
        String web_page = "www.new-web-page.ro";
        ProfessorDto professorDto = ProfessorFactory.generateProfessorDtoBuilder()
                .username("Anonim")
                .webPage(web_page)
                .build();
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        professorService.updateProfessor(professorDto, professorDto.getUsername());
    }

}
