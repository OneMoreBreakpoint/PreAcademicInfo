package data_layer.domain;

import javax.persistence.*;
import java.util.SortedSet;

@Entity
@Table(name = "Teachings")
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "coordinator", nullable = false)
    private boolean coordinator;


    @ManyToMany
    @JoinTable(name = "TeachingsStudents",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private SortedSet<Student> seminarStudents;

    @ManyToMany
    @JoinTable(name = "TeachingsStudents",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private SortedSet<Student> laboratoryStudents;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCoordinator() {
        return coordinator;
    }

    public void setCoordinator(boolean coordinator) {
        this.coordinator = coordinator;
    }

    public SortedSet<Student> getSeminarStudents() {
        return seminarStudents;
    }

    public void setSeminarStudents(SortedSet<Student> seminarStudents) {
        this.seminarStudents = seminarStudents;
    }

    public SortedSet<Student> getLaboratoryStudents() {
        return laboratoryStudents;
    }

    public void setLaboratoryStudents(SortedSet<Student> laboratoryStudents) {
        this.laboratoryStudents = laboratoryStudents;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
