package data_layer.mappers;

import bussiness_layer.dto.UserDTO;
import data_layer.domain.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDTO toUserDTO(User entity) {
        return UserDTO.builder()
                .username(entity.getUsername())
                .password(entity.getEncryptedPassword())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

}
