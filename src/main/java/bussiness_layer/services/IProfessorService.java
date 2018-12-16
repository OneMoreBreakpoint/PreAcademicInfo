package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorRightDto;

import java.util.List;

public interface IProfessorService {
    /**
     * Retrieves enrollments of students from given group taking given course on which prof has rights
     *
     * @return enrollment dtos stripped of lessons that shouldn't be read by given prof according to his rights
     */
    List<EnrollmentDto> getEnrollments(String profUsername, String courseCode, String groupCode);

    List<ProfessorRightDto> getProfessorRights(String profUsername, String courseCode, String groupCode);

    List<GroupDto> getGroups(String profUsername, String courseCode);

    void updateLessons(String profUsername, List<LessonDto> lessonDtos);

    void updateProfessor(ProfessorDto professorDto);

    /**
     * Return a list with all courses taught by a specific professor and the groups linked with, in dto format
     * For the courses coordinated by the given professor it would be returned a list of all groups that have students
     * enrolled at the respective courses
     *
     * @param profUsername
     * @return dto list
     * @throws utils.exceptions.ResourceNotFoundException if the given professor doesn't exist in database
     */
    List<ProfessorCourseDto> getRelatedCourses(String profUsername);

}
