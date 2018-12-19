package data_layer.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "Courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 7)
    //TODO(Norberth) make this unique
    private String code;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    @NotNull
    private Professor coordinator;

    @OneToMany(mappedBy = "course")
    private List<LessonTemplate> lessonTemplates;

}
