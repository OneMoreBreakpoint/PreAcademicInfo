package bussiness_layer.services;

import java.util.List;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorRightDto;
import bussiness_layer.dto.TaughtCourseDto;

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

    List<TaughtCourseDto> getRelatedCourses(String profUsername);

}
