package data_layer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "PartialExams")
@Getter
@Setter
public class PartialExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(1)
    @Max(4)
    private byte nr;

    @Max(10)
    @Min(1)
    private Byte grade;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private PartialExamType type;

    @ManyToOne
    private Enrollment enrollment;

    public enum PartialExamType {
        SEMINAR,
        LABORATORY,
        COURSE
    }


}
