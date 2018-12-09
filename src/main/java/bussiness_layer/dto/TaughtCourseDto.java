package bussiness_layer.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaughtCourseDto {

    @NotNull
    CourseDto courseDto;

    Set<GroupDto> groups;

}
