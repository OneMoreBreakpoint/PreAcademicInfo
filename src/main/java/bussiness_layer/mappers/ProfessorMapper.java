package bussiness_layer.mappers;

import bussiness_layer.dto.ProfessorDto;
import data_layer.domain.Professor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfessorMapper {

    public static Professor toEntity(ProfessorDto dto) {
        Professor entity = new Professor();
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setWebPage(dto.getWebPage());
        entity.setPathToProfilePhoto(dto.getPathToProfilePhoto());
        return entity;
    }

    public static ProfessorDto toDto(Professor entity) {
        ProfessorDto dto = new ProfessorDto();
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setWebPage(entity.getWebPage());
        dto.setPathToProfilePhoto(entity.getPathToProfilePhoto());
        return dto;
    }
}
