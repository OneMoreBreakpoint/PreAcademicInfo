package bussiness_layer.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
