package bussiness_layer.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.LessonType;
import utils.RightType;

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
