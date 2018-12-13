package data_layer.domain;

import lombok.*;
import utils.LessonType;
import utils.RightType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "ProfessorRights")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Group group;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotNull
    private LessonType lessonType;

    @NotNull
    private RightType rightType;

}
