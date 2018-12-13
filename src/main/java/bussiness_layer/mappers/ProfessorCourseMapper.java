package bussiness_layer.mappers;

import java.util.List;

import bussiness_layer.dto.ProfessorCourseDto;
import data_layer.domain.Course;
import data_layer.domain.Group;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfessorCourseMapper {

    public static ProfessorCourseDto toProfessorCourseDto(Course course, List<Group> groups) {
        return ProfessorCourseDto.builder()
                .courseDto(CourseMapper.toDto(course))
                .groups(GroupMapper.toDtoList(groups))
                .build();
    }

//    public static ProfessorCourseDto toDto(List<ProfessorRight> professorRights) {
//        ProfessorCourseDto professorCourseDto = new ProfessorCourseDto();
//        professorCourseDto.setCourse(CourseMapper.toDto(professorRights.get(0).getCourse()));
//        professorCourseDto.setGroups(professorRights.stream()
//                .map(professorRight -> professorRight.getGroup())
//                .collect(Collectors.toSet()));
//        return professorCourseDto;
//    }

}
