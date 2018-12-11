package bussiness_layer.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.dto.ProfessorRightsDto;
import data_layer.domain.Group;
import data_layer.domain.ProfessorCourse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfessorCourseMapper {

    public static ProfessorCourseDto toProfessorCourseDto(ProfessorCourse entity) {

        Set<Group> groupSet = entity.getLaboratoryGroups();
        groupSet.addAll(entity.getSeminarGroups());

        return ProfessorCourseDto.builder()
                .course(CourseMapper.toDto(entity.getCourse()))
                .groups(GroupMapper.toDtoSet(groupSet))
                .build();

//        List<>
//        return ProfessorCourseDto.builder()
//                .course(entity.getCourse())
//                .groups(entity.ge)
//        TeachingDto dto = new TeachingDto();
//        dto.setId(entity.getId());
//        dto.setProfessor(ProfessorMapper.toDto(entity.getProfessor()));
//        dto.setAllGroups(new TreeSet<>());
//        if (entity.getSeminarGroups() != null) {
//            dto.setSeminarGroups(entity.getSeminarGroups().stream().map(GroupMapper::toDto).collect(Collectors.toSet()));
//            dto.getAllGroups().addAll(dto.getSeminarGroups());
//        }
//        if (entity.getLaboratoryGroups() != null) {
//            dto.setLaboratoryGroups(entity.getLaboratoryGroups().stream().map(GroupMapper::toDto).collect(Collectors.toSet()));
//            dto.getAllGroups().addAll(dto.getLaboratoryGroups());
//        }
//        dto.setCourse(CourseMapper.toDto(entity.getCourse()));
//        return dto;
    }

    public static List<ProfessorCourseDto> toProfessorCourseDtoList(List<ProfessorCourse> entities) {
        return entities.stream()
                .map(ProfessorCourseMapper::toProfessorCourseDto)
                .collect(Collectors.toList());
    }

    public static ProfessorRightsDto toProfessorRightsDto(ProfessorCourse entity) {
        return ProfessorRightsDto.builder()
                .laboratoryGroups(entity.getLaboratoryGroups())
                .seminarGroups(entity.getSeminarGroups())
                .build();
    }

}
