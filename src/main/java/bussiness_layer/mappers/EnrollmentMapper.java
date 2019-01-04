package bussiness_layer.mappers;

import bussiness_layer.dto.EnrollmentDto;
import data_layer.domain.Enrollment;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EnrollmentMapper {

    public static Enrollment toEntity(EnrollmentDto dto) {
        Enrollment entity = Enrollment.builder()
                .id(dto.getId())
                .course(CourseMapper.toEntity(dto.getCourse()))
                .student(StudentMapper.toEntity(dto.getStudent()))
                .lessons(LessonMapper.toEntityList(dto.getLessons()))
                .build();
        return entity;
    }

    public static EnrollmentDto toDto(Enrollment entity) {
        EnrollmentDto dto = EnrollmentDto.builder()
                .id(entity.getId())
                .lessons(LessonMapper.toDtoList(entity.getLessons()))
                .course(CourseMapper.toDto(entity.getCourse()))
                .student(StudentMapper.toDto(entity.getStudent()))
                .build();
        return dto;
    }

    public static List<Enrollment> toEntityList(List<EnrollmentDto> dtos) {
        return dtos.stream()
                .map(EnrollmentMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<EnrollmentDto> toDtoList(List<Enrollment> entities) {
        return entities.stream()
                .map(EnrollmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
