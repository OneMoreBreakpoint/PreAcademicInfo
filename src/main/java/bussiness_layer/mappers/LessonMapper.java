package bussiness_layer.mappers;

import java.util.List;
import java.util.stream.Collectors;

import bussiness_layer.dto.EmailNotificationDto;
import bussiness_layer.dto.LessonDto;
import data_layer.domain.Lesson;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LessonMapper {

    public static Lesson toEntity(LessonDto dto) {
        return Lesson.builder()
                .id(dto.getId())
                .template(LessonTemplateMapper.toEntity(dto.getTemplate()))
                .grade(dto.getGrade())
                .bonus(dto.getBonus())
                .attended(dto.isAttended())
                .build();
    }

    public static LessonDto toDto(Lesson entity) {
        return LessonDto.builder()
                .id(entity.getId())
                .template(LessonTemplateMapper.toDto(entity.getTemplate()))
                .grade(entity.getGrade())
                .bonus(entity.getBonus())
                .attended(entity.isAttended())
                .build();
    }

    public static List<LessonDto> toDtoList(List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonMapper::toDto)
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Lesson> toEntityList(List<LessonDto> dtos) {
        return dtos.stream()
                .map(LessonMapper::toEntity)
                .sorted()
                .collect(Collectors.toList());
    }

    public static EmailNotificationDto toEmailNotificationDto(Lesson lessonDto, String emailAdress) {
        return EmailNotificationDto.builder()
                .attended(lessonDto.isAttended())
                .grade(lessonDto.getGrade())
                .bonus(lessonDto.getBonus())
                .emailAddress(emailAdress)
                .courseName(lessonDto.getTemplate().getCourse().getName())
                .lessonType(lessonDto.getTemplate().getType())
                .lessonNumber(lessonDto.getTemplate().getNr())
                .build();
    }

}
