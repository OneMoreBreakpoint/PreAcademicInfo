package bussiness_layer.mappers;

import bussiness_layer.dto.LessonTemplateDto;
import data_layer.domain.LessonTemplate;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class LessonTemplateMapper {

    public static LessonTemplate toEntity(LessonTemplateDto dto) {
        return LessonTemplate.builder()
                .id(dto.getId())
                .weight(dto.getWeight())
                .nr(dto.getNr())
                .type(dto.getType())
                .build();
    }

    public static LessonTemplateDto toDto(LessonTemplate entity) {
        return LessonTemplateDto.builder()
                .id(entity.getId())
                .weight(entity.getWeight())
                .nr(entity.getNr())
                .type(entity.getType())
                .build();
    }

    public static List<LessonTemplate> toEntityList(List<LessonTemplateDto> dtos) {
        return dtos.stream()
                .map(LessonTemplateMapper::toEntity)
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<LessonTemplateDto> toDtoList(List<LessonTemplate> entities) {
        return entities.stream()
                .map(LessonTemplateMapper::toDto)
                .sorted()
                .collect(Collectors.toList());
    }
}
