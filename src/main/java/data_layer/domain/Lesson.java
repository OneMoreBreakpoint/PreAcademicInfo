package data_layer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "Lessons")
@Getter
@Setter
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private boolean attended;

    @Max(10)
    @Min(1)
    private Byte grade;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private LessonType type;

    private Byte nrOfBonusPoints;

    public enum LessonType {
        SEMINAR,
        LABORATORY
    }

}
