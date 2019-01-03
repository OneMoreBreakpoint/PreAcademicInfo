package bussiness_layer.services;

import bussiness_layer.dto.CourseDto;

public interface ICourseService {

    /**
     * Update a course.
     *
     * @param profUsername - professor that initiates the update operation.
     * @param courseDto    - course data.
     */
    void updateCourse(String profUsername, CourseDto courseDto);

    /**
     * Get a course.
     *
     * @param profUsername - professor that initiates the update operation.
     * @param courseCode
     * @return specific CourseDto
     */
    CourseDto getCourse(String profUsername, String courseCode);

}
