package bussiness_layer.mappers;

import bussiness_layer.dto.ProfessorDto;
import data_layer.domain.Professor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfessorMapper {

    public static Professor toEntity(ProfessorDto dto) {
        return Professor.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .email(dto.getEmail())
                .webPage(dto.getWebPage())
                .profilePhoto(dto.getProfilePhoto())
                .build();
    }

    public static ProfessorDto toDto(Professor entity) {
        return ProfessorDto.builder()
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .webPage(entity.getWebPage())
                .profilePhoto(entity.getProfilePhoto())
                .role(entity.getRole())
                .build();
    }
}
