package bussiness_layer.dto;

import lombok.*;
import utils.LessonType;
import utils.RightType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRightDto implements Serializable {

    private static final long serialVersionUID = 2020L;

    @NotNull
    private ProfessorDto professor;
    @NotNull
    private GroupDto group;
    @NotNull
    private CourseDto course;
    @NotNull
    private LessonType lessonType;
    @NotNull
    private RightType rightType;

}
