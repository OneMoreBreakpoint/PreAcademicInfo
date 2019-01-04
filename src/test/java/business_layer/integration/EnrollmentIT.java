package business_layer.integration;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.services.impl.EnrollmentService;
import data_layer.domain.Enrollment;
import utils.TestConstants;
import utils.exceptions.ResourceNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnrollmentIT extends BaseIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

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

}
