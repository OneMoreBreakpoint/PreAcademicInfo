package data_layer.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Teachings")
@Getter
@Setter
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "TeachingsStudents",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @SortNatural
    private List<Student> seminarStudents;

    @ManyToMany
    @JoinTable(name = "TeachingsStudents",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @SortNatural
    private List<Student> laboratoryStudents;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
