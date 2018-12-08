package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.TeachingDto;

import java.util.List;

public interface IProfessorService {
    List<EnrollmentDto> getEnrollments(String profUsername, String courseCode, String groupCode);

    TeachingDto getTeaching(String profUsername, String courseCode);

    void updateEnrollments(String profUsername, List<EnrollmentDto> enrollments);
}
