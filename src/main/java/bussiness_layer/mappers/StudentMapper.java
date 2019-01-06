package bussiness_layer.mappers;

import bussiness_layer.dto.StudentDto;
import data_layer.domain.Student;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StudentMapper {

    public static Student toEntity(StudentDto dto) {
        Student entity = new Student();
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setFathersInitials(dto.getFathersInitials());
        entity.setProfilePhoto(dto.getProfilePhoto());
        entity.setNotifiedByEmail(dto.isNotifiedByEmail());
        entity.setGroup(GroupMapper.toEntity(dto.getGroup()));
        return entity;
    }

    public static StudentDto toDto(Student entity) {
        StudentDto dto = new StudentDto();
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setRegistrationNr(entity.getRegistrationNr());
        dto.setFathersInitials(entity.getFathersInitials());
        dto.setProfilePhoto(entity.getProfilePhoto());
        dto.setNotifiedByEmail(entity.isNotifiedByEmail());
        dto.setGroup(GroupMapper.toDto(entity.getGroup()));
        dto.setUserRole(entity.getUserRole());
        return dto;
    }
}
