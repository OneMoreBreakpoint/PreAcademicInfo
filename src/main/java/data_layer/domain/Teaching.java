package data_layer.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SortNatural;

import lombok.Getter;
import lombok.Setter;

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
