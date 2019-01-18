package bussiness_layer.mappers;

import bussiness_layer.dto.ProfessorRightDto;
import data_layer.domain.ProfessorRight;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ProfessorRightMapper {

    public static ProfessorRight toEntity(ProfessorRightDto dto) {
        return ProfessorRight.builder()
                .build();
    }

    public static ProfessorRightDto toDto(ProfessorRight entity) {
        return ProfessorRightDto.builder()
                .course(CourseMapper.toDto(entity.getCourse()))
                .group(GroupMapper.toDto(entity.getGroup()))
                .lessonType(entity.getLessonType())
                .rightType(entity.getRightType())
                .build();
    }

    public static List<ProfessorRightDto> toDtoList(List<ProfessorRight> professorRights) {
        return professorRights.stream()
                .map(ProfessorRightMapper::toDto)
                .collect(Collectors.toList());
    }

}
