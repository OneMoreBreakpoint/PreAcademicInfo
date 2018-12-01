package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.GroupDTO;
import bussiness_layer.dto.TeachingDTO;

import java.util.List;
import java.util.SortedSet;

public interface IProfessorService {
    List<EnrollmentDTO> getEnrollmentsByCourseAndGroup(String profUsername, String courseCode, Short groupCode);
    TeachingDTO getTeachingByProfessorAndCourse(String profUsername, String courseCode);
    void updateEnrollments(List<EnrollmentDTO> enrollments);
}
