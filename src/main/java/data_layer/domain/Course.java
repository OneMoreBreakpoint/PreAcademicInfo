package data_layer.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Column(unique = true)
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
