package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.services.IEnrollmentService;
import bussiness_layer.services.IStudentService;
import data_layer.domain.Enrollment;
import data_layer.domain.Student;
import factory.StudentFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import utils.TestConstants;
import utils.exceptions.ResourceNotFoundException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnrollmentIT extends BaseIntegrationTest {

    @Autowired
    private IEnrollmentService enrollmentService;

    @Autowired
    IStudentService studentService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Transactional
    public void givenProfessorHasRights_whenGetEnrollments_thenEnrollmentsRetrieved() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //When
        List<EnrollmentDto> enrollmentDtoList = enrollmentService.getEnrollments(
                TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        assertNotNull(enrollmentDtoList);
        assertEquals(1, enrollmentDtoList.size());
    }

    @Test
    public void givenCourseDoesNotExist_whenGetEnrollments_thenResourceNotFoundExceptionThrown() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        createProfessorRights(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        List<EnrollmentDto> enrollmentDtoList = enrollmentService.getEnrollments(
                TestConstants.PROF_USERNAME, "__", TestConstants.GROUP_CODE);
    }

    @Test
    @Transactional
    public void givenStudentHasEnrollments_whenGetEnrollments_thenEnrollmentsRetrieved() {
        //Given
        Enrollment enrollmentSaved = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        String studUsername = enrollmentSaved.getStudent().getUsername();
        //When
        List<EnrollmentDto> enrollmentsRetrieved = enrollmentService.getEnrollments(studUsername);
        //Then
        assertNotNull(enrollmentsRetrieved);
        assertEquals(1, enrollmentsRetrieved.size());
        assertEquals(enrollmentSaved.getId(), enrollmentsRetrieved.get(0).getId());
    }

    @Test
    public void givenStudentDoesNotHaveEnrollments_whenGetEnrollments_thenResourceNotFoundExceptionThrown() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        enrollmentService.getEnrollments("__");
    }

    @Test
    @Transactional
    public void givenStudentDto_whenUpdateStudent_thenStudentIsUpdated() {
        //Given
        Student student = createStudent(TestConstants.STUD_USERNAME);
        String image = TestConstants.IMAGE2;
        StudentDto studentDto = StudentFactory.generateStudentDtoBuilder()
                .username(student.getUsername())
                .profilePhoto(image)
                .build();
        //When
        studentService.updateStudent(studentDto, studentDto.getUsername());
        //Then
        assertEquals(image, studentDto.getProfilePhoto());
    }

    @Test
    @Transactional
    public void givenStudentDoesNotExist_whenUpdateStudent_thenResourceNotFoundExceptionThrown() {
        //Given
        Student student = createStudent(TestConstants.STUD_USERNAME);
        String image = TestConstants.IMAGE2;
        StudentDto studentDto = StudentFactory.generateStudentDtoBuilder()
                .username("Anonim")
                .profilePhoto(image)
                .build();
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        studentService.updateStudent(studentDto, studentDto.getUsername());
    }
}
