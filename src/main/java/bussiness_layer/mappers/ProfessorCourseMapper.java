package bussiness_layer.mappers;

import bussiness_layer.dto.ProfessorCourseDto;
import data_layer.domain.Course;
import data_layer.domain.Group;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ProfessorCourseMapper {

    public static ProfessorCourseDto toDto(Course course, List<Group> groups, boolean profIsCoordinator) {
        return ProfessorCourseDto.builder()
                .course(CourseMapper.toDto(course))
                .groups(GroupMapper.toDtoList(groups))
                .coordinator(profIsCoordinator)
                .build();
    }

}
