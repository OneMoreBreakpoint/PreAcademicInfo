package factory;

import bussiness_layer.dto.LessonDto;
import data_layer.domain.Lesson;

public class LessonFactory {
    public static Lesson.LessonBuilder generateLessonBuilder() {
        return Lesson.builder()
                .attended(false)
                .type(Lesson.LessonType.SEMINAR)
                .nr((byte) 1);
    }

    public static Lesson generateLesson() {
        return generateLessonBuilder().build();
    }

    public static LessonDto.LessonDtoBuilder generateLessonDtoBuilder() {
        return LessonDto.builder()
                .attended(false)
                .type(Lesson.LessonType.SEMINAR)
                .nr((byte) 1);
    }

    public static LessonDto generateLessonDto() {
        return generateLessonDtoBuilder().build();
    }


}
