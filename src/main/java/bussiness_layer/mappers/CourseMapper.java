package bussiness_layer.mappers;

import bussiness_layer.dto.CourseDto;
import data_layer.domain.Course;
import data_layer.domain.ProfessorRight;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CourseMapper {

    public static Course toEntity(CourseDto dto) {
        return Course.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .name(dto.getName())
                .lessonTemplates(LessonTemplateMapper.toEntityList(dto.getLessonTemplates()))
                .coordinator(ProfessorMapper.toEntity(dto.getCoordinator()))
                .build();
    }

    public static CourseDto toDto(Course entity) {
        return CourseDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .coordinator(ProfessorMapper.toDto(entity.getCoordinator()))
                .lessonTemplates(LessonTemplateMapper.toDtoList(entity.getLessonTemplates()))
                .build();
    }

    public static List<Course> toCoursesList(List<ProfessorRight> professorRights) {
        return professorRights.stream()
                .map(ProfessorRight::getCourse)
                .distinct()
                .collect(Collectors.toList());
    }
}
