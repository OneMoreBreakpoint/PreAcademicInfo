package bussiness_layer.mappers;

import bussiness_layer.dto.LessonDto;
import data_layer.domain.Lesson;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class LessonMapper {

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

    public static List<LessonDto> toDtoList(List<Lesson> lessons){
        return lessons.stream()
                .map(LessonMapper::toDto)
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Lesson> toEntityList(List<LessonDto> dtos){
        return dtos.stream()
                .map(LessonMapper::toEntity)
                .sorted()
                .collect(Collectors.toList());
    }
}
