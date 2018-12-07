package bussiness_layer.mappers;

import bussiness_layer.dto.PartialExamDto;
import data_layer.domain.PartialExam;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PartialExamDtoMapper {

    public static PartialExam toEntity(PartialExamDto examDTO) {
        PartialExam entity = new PartialExam();
        entity.setId(examDTO.getId());
        entity.setNr(examDTO.getNr());
        entity.setGrade(examDTO.getGrade());
        entity.setType(examDTO.getType());
        return entity;
    }

    public static PartialExamDto toDto(PartialExam partialExam) {
        PartialExamDto dto = new PartialExamDto();
        dto.setId(partialExam.getId());
        dto.setNr(partialExam.getNr());
        dto.setGrade(partialExam.getGrade());
        dto.setType(partialExam.getType());
        return dto;
    }
}
