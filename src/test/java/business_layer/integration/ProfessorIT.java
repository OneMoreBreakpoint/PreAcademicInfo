package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;
import bussiness_layer.dto.TeachingDto;
import bussiness_layer.mappers.CourseMapper;
import bussiness_layer.services.IProfessorService;
import data_layer.domain.Enrollment;
import data_layer.domain.Lesson;
import factory.StudentFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import utils.TestConstants;
import utils.exceptions.ResourceNotFoundException;
import utils.exceptions.UnprocessableEntityException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProfessorIT extends BaseIntegrationTest {

    @Autowired
    private IProfessorService professorService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Transactional
    public void givenProfessorHasRights_whenGetEnrollments_thenEnrollmentsRetrieved() {
        //Given
        createTeachingAndEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        List<EnrollmentDto> enrollmentDtoList = professorService.getEnrollments(
                TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);

        //Then
        assertNotNull(enrollmentDtoList);
        assertEquals(enrollmentDtoList.size(), 1);
    }

    @Test
    @Transactional
    public void givenCourseDoesNotExist_whenGetEnrollments_thenResourceNotFoundExceptionThrown() {
        //Given
        createTeachingAndEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        List<EnrollmentDto> enrollmentDtoList = professorService.getEnrollments(
                TestConstants.PROF_USERNAME, "__", TestConstants.GROUP_CODE);
    }

    @Test
    @Transactional
    public void givenProfessorTeachesAtCourse_whenGetTeaching_thenTeachingRetrieved() {
        //Given
        createTeachingAndEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        TeachingDto teaching = professorService.getTeaching(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE);
        //Then
        assertNotNull(teaching);
    }

    @Test
    @Transactional
    public void givenProfessorDoesNotTeachAtCourse_whenGetTeaching_thenResourceNotFoundExceptionThrown() {
        //Given
        createTeachingAndEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        professorService.getTeaching(TestConstants.PROF_USERNAME, "__");
    }

    @Test
    @Ignore
    @Transactional
    public void givenProfessorHasRights_whenUpdateEnrollments_thenEnrollmentsAreUpdated() {
        //Given
        Enrollment enrollment = createTeachingAndEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        int lessonId = enrollment.getLessons().get(0).getId();
        int examId = enrollment.getPartialExams().get(0).getId();
        List<EnrollmentDto> enrollmentsBeforeUpdate = Arrays.asList(EnrollmentDto.builder()
                .lessons(Arrays.asList(LessonDto.builder()
                        .id(lessonId)
                        .attended(false)
                        .grade((byte) 10)
                        .nr((byte) 1)
                        .type(Lesson.LessonType.LABORATORY)
                        .build()))
                .partialExams(Arrays.asList(PartialExamDto.builder()
                        .id(examId)
                        .grade((byte) 10)
                        .nr((byte) 1)
                        .build()))
                .student(StudentFactory.generateStudentDto())
                .course(CourseMapper.toDto(courseRepository.findByCode(TestConstants.COURSE_CODE)))
                .build());
        //When
        professorService.updateEnrollments(TestConstants.PROF_USERNAME, enrollmentsBeforeUpdate);
        //Then
        List<EnrollmentDto> enrollmentsAfterUpdate = professorService.getEnrollments(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        assertEquals((byte) 10, (byte) enrollmentsAfterUpdate.get(0).getLessons().get(0).getGrade());
        assertEquals((byte) 10, (byte) enrollmentsAfterUpdate.get(0).getPartialExams().get(0).getGrade());
    }

    @Test
    @Transactional
    public void givenNoEnrollmentsAreGiven_whenUpdateEnrollments_throwUnproccesableEntityExceptionThrown() {
        //Given
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();
        //Then
        exception.expect(UnprocessableEntityException.class);
        //When
        professorService.updateEnrollments(TestConstants.PROF_USERNAME, enrollmentDtos);
    }

}
