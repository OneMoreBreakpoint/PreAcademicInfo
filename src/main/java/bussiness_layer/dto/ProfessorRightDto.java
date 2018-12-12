package bussiness_layer.dto;

import data_layer.domain.Course;
import data_layer.domain.Group;
import data_layer.domain.Professor;
import lombok.*;
import utils.LessonType;
import utils.RightType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRightDto {

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
