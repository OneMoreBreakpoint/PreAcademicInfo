package bussiness_layer.mappers;

import bussiness_layer.dto.EnrollmentDto;
import data_layer.domain.Enrollment;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EnrollmentMapper {

    public static Enrollment toEntity(EnrollmentDto dto) {
        Enrollment entity = new Enrollment();
        entity.setId(dto.getId());
        entity.setStudent(StudentMapper.toEntity(dto.getStudent()));
        entity.setPartialExams(dto.getPartialExams().stream().map(PartialExamMapper::toEntity).collect(Collectors.toList()));
        entity.setLessons(dto.getLessons().stream().map(LessonMapper::toEntity).collect(Collectors.toList()));
        entity.setCourse(CourseMapper.toEntity(dto.getCourse()));
        return entity;
    }

    public static EnrollmentDto toDto(Enrollment entity) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(entity.getId());
        dto.setStudent(StudentMapper.toDto(entity.getStudent()));
        if (entity.getPartialExams() != null) {
            dto.setPartialExams(entity.getPartialExams().stream().map(PartialExamMapper::toDto).collect(Collectors.toList()));
        }
        if (entity.getLessons() != null) {
            dto.setLessons(entity.getLessons().stream().map(LessonMapper::toDto).collect(Collectors.toList()));
        }
        dto.setCourse(CourseMapper.toDto(entity.getCourse()));
        return dto;
    }

    public static List<Enrollment> toEntityList(List<EnrollmentDto> dtos) {
        return dtos.stream()
                .map(EnrollmentMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<EnrollmentDto> toEntityDtoList(List<Enrollment> entities) {
        return entities.stream().map(EnrollmentMapper::toDto).collect(Collectors.toList());
    }
}
