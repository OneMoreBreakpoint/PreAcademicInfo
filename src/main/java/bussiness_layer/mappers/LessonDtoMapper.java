package bussiness_layer.mappers;

import bussiness_layer.dto.LessonDto;
import data_layer.domain.Lesson;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LessonDtoMapper {

    public static Lesson toEntity(LessonDto lessonDTO) {
        Lesson entity = new Lesson();
        entity.setId(lessonDTO.getId());
        entity.setNr(lessonDTO.getNr());
        entity.setGrade(lessonDTO.getGrade());
        entity.setType(lessonDTO.getType());
        entity.setBonus(lessonDTO.getBonus());
        entity.setAttended(lessonDTO.isAttended());
        return entity;
    }

    public static LessonDto toDto(Lesson lesson) {
        LessonDto lessonDTO = new LessonDto();
        lessonDTO.setId(lesson.getId());
        lessonDTO.setNr(lesson.getNr());
        lessonDTO.setGrade(lesson.getGrade());
        lessonDTO.setType(lesson.getType());
        lessonDTO.setBonus(lesson.getBonus());
        lessonDTO.setAttended(lesson.isAttended());
        return lessonDTO;
    }
}
