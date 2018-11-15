package data_layer.domain;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teachings")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Student> getSeminarStudents() {
        return seminarStudents;
    }

    public void setSeminarStudents(List<Student> seminarStudents) {
        this.seminarStudents = seminarStudents;
    }

    public List<Student> getLaboratoryStudents() {
        return laboratoryStudents;
    }

    public void setLaboratoryStudents(List<Student> laboratoryStudents) {
        this.laboratoryStudents = laboratoryStudents;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
