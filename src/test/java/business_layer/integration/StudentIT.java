package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.services.IStudentService;
import data_layer.domain.Enrollment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import data_layer.domain.Student;
import factory.StudentFactory;
import utils.TestConstants;
import utils.exceptions.ResourceNotFoundException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StudentIT extends BaseIntegrationTest {

    @Autowired
    private IStudentService studentService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Transactional
    public void givenStudentHasEnrollments_whenGetEnrollments_thenEnrollmentsRetrieved() {
        //Given
        Enrollment enrollmentSaved = createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        String studUsername = enrollmentSaved.getStudent().getUsername();
        //When
        List<EnrollmentDto> enrollmentsRetrieved = studentService.getEnrollments(studUsername);
        //Then
        assertNotNull(enrollmentsRetrieved);
        assertEquals(1, enrollmentsRetrieved.size());
        assertEquals(enrollmentSaved.getId(), enrollmentsRetrieved.get(0).getId());
    }

    @Test
    @Transactional
    public void givenStudentDoesNotHaveEnrollments_whenGetEnrollments_thenResourceNotFoundExceptionThrown() {
        //Given
        createEnrollment(TestConstants.PROF_USERNAME, TestConstants.COURSE_CODE, TestConstants.GROUP_CODE);
        //Then
        exception.expect(ResourceNotFoundException.class);
        //When
        studentService.getEnrollments("__");
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
