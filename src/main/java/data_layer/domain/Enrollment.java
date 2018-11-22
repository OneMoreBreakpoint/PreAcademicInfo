package data_layer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Enrollments")
@Getter
@Setter
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany
    @JoinColumn(name = "enrollment_id")
    private List<PartialExam> partialExams;

    @OneToMany
    @JoinColumn(name = "enrollment_id")
    private List<Lesson> lessons;

}
