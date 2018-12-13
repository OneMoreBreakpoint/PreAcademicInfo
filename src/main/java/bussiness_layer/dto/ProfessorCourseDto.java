package bussiness_layer.dto;

import java.util.List;

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

    @NotNull
    CourseDto course;

    @NotNull
    List<GroupDto> groups;

}
