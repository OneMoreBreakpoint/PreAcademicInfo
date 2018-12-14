package bussiness_layer.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.LessonType;
import utils.RightType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto implements Comparable<LessonDto>, Serializable {

    private static final long serialVersionUID = 2024L;

    private Integer id;

    @Min(1)
    @Max(14)
    private Byte nr;

    @NotNull
    private boolean attended;

    @Max(10)
    @Min(1)
    private Byte grade;

    @NotNull
    private LessonType type;

    @Min(-10)
    @Max(10)
    private Byte bonus;

    private RightType rightType;

    @Override
    public int compareTo(LessonDto o) {
        if (this.type.ordinal() <= 1 && o.type.ordinal() > 1) { //if this=SEM||LAB and o=PARTIAL
            return -1;
        }
        if (this.type.ordinal() > 1 && o.type.ordinal() <= 1) {
            return 1;
        }
        //if both are SEM||LAB or both PARTIALS
        int nrDiff = this.nr - o.nr;
        if (nrDiff != 0) {
            return nrDiff;
        }
        return this.getType().ordinal() - o.getType().ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LessonDto)) {
            return false;
        }
        return this.compareTo((LessonDto) o) == 0;
    }
}
