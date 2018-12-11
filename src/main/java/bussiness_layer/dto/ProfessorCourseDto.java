package bussiness_layer.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorCourseDto {

    //TODO(Norberth) check if this should be a sorted set, or maybe use on the predefined sorted methods in jpa repository
    @NotNull
    private Set<GroupDto> groups;

    @NotNull
    private CourseDto course;

}
