package bussiness_layer.mappers;

import bussiness_layer.dto.GroupDto;
import data_layer.domain.Group;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GroupDtoMapper {
    static Group toEntity(GroupDto dto) {
        Group entity = new Group();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        return entity;
    }

    public static GroupDto toDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        return dto;
    }
}
