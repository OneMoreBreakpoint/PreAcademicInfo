package bussiness_layer.mappers;

import bussiness_layer.dto.TeachingDto;
import data_layer.domain.Teaching;
import lombok.experimental.UtilityClass;

import java.util.TreeSet;
import java.util.stream.Collectors;

@UtilityClass
public class TeachingMapper {

    public static Teaching toEntity(TeachingDto dto) {
        //TODO:
        return null;
    }

    public static TeachingDto toDto(Teaching entity) {
        TeachingDto dto = new TeachingDto();
        dto.setId(entity.getId());
        dto.setProfessor(ProfessorMapper.toDto(entity.getProfessor()));
        dto.setAllGroups(new TreeSet<>());
        if (entity.getSeminarGroups() != null) {
            dto.setSeminarGroups(entity.getSeminarGroups().stream().map(GroupMapper::toDto).collect(Collectors.toSet()));
            dto.getAllGroups().addAll(dto.getSeminarGroups());
        }
        if (entity.getLaboratoryGroups() != null) {
            dto.setLaboratoryGroups(entity.getLaboratoryGroups().stream().map(GroupMapper::toDto).collect(Collectors.toSet()));
            dto.getAllGroups().addAll(dto.getLaboratoryGroups());
        }
        dto.setCourse(CourseMapper.toDto(entity.getCourse()));
        return dto;
    }
}
