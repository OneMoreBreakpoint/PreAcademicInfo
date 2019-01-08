package web_layer.utils;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.LessonTemplateDto;
import lombok.experimental.UtilityClass;
import utils.LessonType;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@UtilityClass
public class ViewHelper {
    public static boolean shouldRenderAverageCell(EnrollmentDto enrollmentTemplate) {
        return enrollmentTemplate.getLessons().stream()
                .anyMatch(lessonDto ->
                        lessonDto.getTemplate().getType() == LessonType.LABORATORY
                                || lessonDto.getTemplate().getType() == LessonType.PARTIAL_EXAM_SEMINAR
                                || lessonDto.getTemplate().getType() == LessonType.PARTIAL_EXAM_LABORATORY
                                || lessonDto.getTemplate().getType() == LessonType.PARTIAL_EXAM_COURSE
                );
    }

    public static boolean shouldRenderTotalSemAttendanceCell(EnrollmentDto enrollmentTemplate) {
        return enrollmentTemplate.getLessons().stream()
                .anyMatch(lessonDto -> lessonDto.getTemplate().getType() == LessonType.SEMINAR);
    }

    public static boolean shouldRenderTotalLabAttendanceCell(EnrollmentDto enrollmentTemplate) {
        return enrollmentTemplate.getLessons().stream()
                .anyMatch(lessonDto -> lessonDto.getTemplate().getType() == LessonType.LABORATORY);
    }

    public static LessonDto getRealLessonFromTemplate(List<LessonDto> realLessons, LessonDto templateLesson) {
        List<LessonDto> result = realLessons.stream()
                .filter(lesson -> lesson.equals(templateLesson))
                .collect(Collectors.toList());
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public static SortedSet<LessonDto> getLessonsTemplateSet(List<EnrollmentDto> enrollmentDtos) {
        SortedSet<LessonDto> lessonDtosTemplateSet = new TreeSet<>();
        enrollmentDtos.forEach(enrollmentDto -> lessonDtosTemplateSet.addAll(enrollmentDto.getLessons()));
        return lessonDtosTemplateSet;
    }

    public static long getNrOfLessonsOfType(List<LessonTemplateDto> lessonTemplateDtos, String type) {
        return lessonTemplateDtos.stream()
                .filter(lessonDto -> lessonDto.getType() == LessonType.valueOf(type))
                .count();
    }
}
