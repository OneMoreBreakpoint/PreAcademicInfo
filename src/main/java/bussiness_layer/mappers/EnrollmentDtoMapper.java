package bussiness_layer.mappers;

import bussiness_layer.dto.EnrollmentDto;
import data_layer.domain.Enrollment;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class EnrollmentDtoMapper {

    public static Enrollment toEntity(EnrollmentDto dto) {
        Enrollment entity = new Enrollment();
        entity.setId(dto.getId());
        entity.setStudent(StudentDtoMapper.toEntity(dto.getStudent()));
        entity.setPartialExams(dto.getPartialExams().stream().map(PartialExamDtoMapper::toEntity).collect(Collectors.toList()));
        entity.setLessons(dto.getLessons().stream().map(LessonDtoMapper::toEntity).collect(Collectors.toList()));
        entity.setCourse(CourseDtoMapper.toEntity(dto.getCourse()));
        return entity;
    }

    public static EnrollmentDto toDto(Enrollment entity) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(entity.getId());
        dto.setStudent(StudentDtoMapper.toDto(entity.getStudent()));
        dto.setPartialExams(entity.getPartialExams().stream().map(PartialExamDtoMapper::toDto).collect(Collectors.toList()));
        dto.setLessons(entity.getLessons().stream().map(LessonDtoMapper::toDto).collect(Collectors.toList()));
        dto.setCourse(CourseDtoMapper.toDto(entity.getCourse()));
        return dto;
    }
}
