package bussiness_layer.services;

import java.util.List;

import bussiness_layer.dto.EmailNotificationDto;
import bussiness_layer.dto.GroupDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.ProfessorRightDto;

public interface IProfessorService {

    /**
     * Return a list of professor rights in dto format.
     *
     * @param profUsername
     * @param courseCode
     * @param groupCode
     * @return
     */
    List<ProfessorRightDto> getProfessorRights(String profUsername, String courseCode, String groupCode);

    /**
     * Return for a given professor and course all correlated groups in dto format.
     *
     * @param profUsername
     * @param courseCode
     * @return
     */
    List<GroupDto> getGroups(String profUsername, String courseCode);

    /**
     * Update lessons data via a list of lessonDtos
     *
     * @param profUsername - professor that initiates the update operation
     * @param lessonDtos   - lessons data
     * @return a list of email notifications in dto format in order to be used by the email sender component
     */
    List<EmailNotificationDto> updateLessons(String profUsername, List<LessonDto> lessonDtos);

    void updateProfessor(ProfessorDto professorDto, String profUsername);

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
