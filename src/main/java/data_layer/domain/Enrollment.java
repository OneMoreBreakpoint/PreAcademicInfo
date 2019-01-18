package data_layer.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Enrollments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "enrollment")
    private List<Lesson> lessons;

}
