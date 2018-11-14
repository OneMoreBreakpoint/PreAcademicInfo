package data_layer.domain;

import javax.persistence.*;

@Entity
@Table(name = "PartialExams")
public class PartialExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grade")
    private Byte grade;

    @Column(name = "type", nullable = false)
    private PartialExamType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getGrade() {
        return grade;
    }

    public void setGrade(Byte grade) {
        this.grade = grade;
    }

    public PartialExamType getType() {
        return type;
    }

    public void setType(PartialExamType type) {
        this.type = type;
    }

    public enum PartialExamType{
        SEMINAR,
        LABORATORY,
        COURSE
    }
}
