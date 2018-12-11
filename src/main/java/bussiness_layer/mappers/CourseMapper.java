package bussiness_layer.mappers;

import bussiness_layer.dto.CourseDto;
import data_layer.domain.Course;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CourseMapper {

    public static Course toEntity(CourseDto dto) {
        Course entity = new Course();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setNrOfSeminars(dto.getNrOfSeminars());
        entity.setNrOfLaboratories(dto.getNrOfLaboratories());
        entity.setCoordinator(ProfessorMapper.toEntity(dto.getCoordinator()));
        return entity;
    }

    public static CourseDto toDto(Course entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setNrOfSeminars(entity.getNrOfSeminars());
        dto.setNrOfLaboratories(entity.getNrOfLaboratories());
        dto.setCoordinator(ProfessorMapper.toDto(entity.getCoordinator()));
        return dto;
    }
}
