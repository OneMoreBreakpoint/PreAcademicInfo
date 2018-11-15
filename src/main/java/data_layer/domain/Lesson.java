package data_layer.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "Lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "attended", nullable = false)
    private boolean attended;

    @Max(10) @Min(1)
    @Column(name = "grade")
    private Byte grade;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private LessonType type;

    @Column(name = "nr_of_bonus_points")
    private Byte nrOfBonusPoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Byte getGrade() {
        return grade;
    }

    public void setGrade(Byte grade) {
        this.grade = grade;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public Byte getNrOfBonusPoints() {
        return nrOfBonusPoints;
    }

    public void setNrOfBonusPoints(Byte nrOfBonusPoints) {
        this.nrOfBonusPoints = nrOfBonusPoints;
    }

    public enum LessonType{
        SEMINAR,
        LABORATORY
    }
}
