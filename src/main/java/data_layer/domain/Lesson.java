package data_layer.domain;

import lombok.*;
import utils.LessonType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "Lessons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(1)
    @Max(14)
    @NotNull
    private byte nr;

    private boolean attended;

    @Max(10)
    @Min(0)
    private Byte grade;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private LessonType type;

    @Min(-10)
    @Max(10)
    private Byte bonus;

    @NotNull
    @ManyToOne
    private Enrollment enrollment;

}
