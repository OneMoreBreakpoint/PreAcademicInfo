package data_layer.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Enrollments")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<PartialExam> getPartialExams() {
        return partialExams;
    }

    public void setPartialExams(List<PartialExam> partialExams) {
        this.partialExams = partialExams;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
