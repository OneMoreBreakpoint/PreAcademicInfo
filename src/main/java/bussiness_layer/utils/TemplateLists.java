package bussiness_layer.utils;

import java.util.List;
import java.util.stream.Collectors;

import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;
import data_layer.domain.Lesson;
import data_layer.domain.PartialExam;

public class TemplateLists {
    public static LessonDto getRealLessonFromTemplate(List<LessonDto> realLessons, LessonDto templateLesson) {
        List<LessonDto> result = realLessons.stream().filter(lesson ->
                (lesson.getType().equals(templateLesson.getType())) && (lesson.getNr().equals(templateLesson.getNr()))).collect(Collectors.toList());
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public static PartialExamDto getRealPartialExamFromTemplate(List<PartialExamDto> realPartialExams, PartialExamDto templatePartialExam) {
        List<PartialExamDto> result = realPartialExams.stream().filter(lesson ->
                (lesson.getType().equals(templatePartialExam.getType())) && (lesson.getNr() == templatePartialExam.getNr())).collect(Collectors.toList());
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }
}
