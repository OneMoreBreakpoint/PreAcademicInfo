package bussiness_layer.mappers;

import bussiness_layer.dto.TeachingDto;
import data_layer.domain.Teaching;
import lombok.experimental.UtilityClass;

import java.util.TreeSet;
import java.util.stream.Collectors;

@UtilityClass
public class TeachingDtoMapper {

    public static Teaching toEntity(TeachingDto dto) {
        //TODO:
        return null;
    }

    public static TeachingDto toDto(Teaching entity) {
        TeachingDto dto = new TeachingDto();
        dto.setId(entity.getId());
        dto.setProfessor(ProfessorDtoMapper.toDto(entity.getProfessor()));
        dto.setSeminarGroups(entity.getSeminarGroups().stream().map(GroupDtoMapper::toDto).collect(Collectors.toSet()));
        dto.setLaboratoryGroups(entity.getLaboratoryGroups().stream().map(GroupDtoMapper::toDto).collect(Collectors.toSet()));
        dto.setAllGroups(new TreeSet<>());
        dto.getAllGroups().addAll(dto.getSeminarGroups());
        dto.getAllGroups().addAll(dto.getLaboratoryGroups());
        dto.setCourse(CourseDtoMapper.toDto(entity.getCourse()));
        return dto;
    }
}
