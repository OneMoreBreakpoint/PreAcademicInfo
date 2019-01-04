package bussiness_layer.mappers;

import bussiness_layer.dto.StudentDto;
import data_layer.domain.Student;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StudentMapper {

    public static Student toEntity(StudentDto dto) {
        return Student.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .fathersInitials(dto.getFathersInitials())
                .pathToProfilePhoto(dto.getPathToProfilePhoto())
                .notifiedByEmail(dto.isNotifiedByEmail())
                .group(GroupMapper.toEntity(dto.getGroup()))
                .build();
    }

    public static StudentDto toDto(Student entity) {
        return StudentDto.builder()
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .registrationNr(entity.getRegistrationNr())
                .fathersInitials(entity.getFathersInitials())
                .pathToProfilePhoto(entity.getPathToProfilePhoto())
                .notifiedByEmail(entity.isNotifiedByEmail())
                .group(GroupMapper.toDto(entity.getGroup()))
                .role(entity.getRole())
                .build();
    }
}
