package data_layer.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "PartialExams")
@Getter
@Setter
public class PartialExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(10)
    @Min(1)
    private Byte grade;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private PartialExamType type;

    public enum PartialExamType {
        SEMINAR,
        LABORATORY,
        COURSE
    }
}
