package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.GroupDTO;

import java.util.List;
import java.util.SortedSet;

public interface IProfessorService {
    List<EnrollmentDTO> getEnrollmentsByCourseAndGroup(String courseCode, Short groupCode);
    SortedSet<GroupDTO> getGroupsByProfessorAndCourse(String profUsername, String courseCode);
}
