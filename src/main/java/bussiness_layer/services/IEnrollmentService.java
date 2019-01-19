package bussiness_layer.services;

import java.util.List;

import bussiness_layer.dto.EnrollmentDto;

public interface IEnrollmentService {

    /**
     * Retrieves enrollments of students from given group taking given course on which prof has rights.
     *
     * @return enrollment dtos stripped of lessons that shouldn't be read by given prof according to his rights
     */
    List<EnrollmentDto> getEnrollments(String profUsername, String courseCode, String groupCode);

    /**
     * Get all the enrollments that belong to a student.
     *
     * @param studUsername - the username to identify the student
     * @return - the enrollments for the  given student
     */
    List<EnrollmentDto> getEnrollments(String studUsername);

    public String generateExcelExportFile(String profUsername, String courseCode, String groupCode, boolean publicType);

}
