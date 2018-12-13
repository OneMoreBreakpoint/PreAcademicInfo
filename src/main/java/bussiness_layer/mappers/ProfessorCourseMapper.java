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
                .course(CourseMapper.toDto(course))
                .groups(GroupMapper.toDtoList(groups))
                .build();
    }

}
