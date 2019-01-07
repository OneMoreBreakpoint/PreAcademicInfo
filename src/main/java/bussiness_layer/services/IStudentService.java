package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.StudentDto;

import java.util.List;

public interface IStudentService {
    /**
     * Get all the enrollments that belong to a student.
     *
     * @param studUsername - the username to identify the student
     * @return - the enrollments for the  given student
     */
    List<EnrollmentDto> getEnrollments(String studUsername);

    void updateStudent(StudentDto studentDto, String studUsername);
}
