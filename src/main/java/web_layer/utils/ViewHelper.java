package web_layer.utils;

import bussiness_layer.dto.EnrollmentDto;
import lombok.experimental.UtilityClass;
import utils.LessonType;

@UtilityClass
public class ViewHelper {
    public static boolean shouldRenderAverageCell(EnrollmentDto enrollmentTemplate){
        return enrollmentTemplate.getLessons().stream()
                .anyMatch(lessonDto ->
                    lessonDto.getType() == LessonType.LABORATORY
                            || lessonDto.getType() == LessonType.PARTIAL_EXAM_SEMINAR
                            || lessonDto.getType() == LessonType.PARTIAL_EXAM_LABORATORY
                            || lessonDto.getType() == LessonType.PARTIAL_EXAM_COURSE
                );
    }
}
