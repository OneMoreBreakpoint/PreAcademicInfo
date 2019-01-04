package bussiness_layer.dto;

import lombok.*;
import utils.LessonType;
import utils.RightType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonTemplateDto implements Comparable<LessonTemplateDto>, Serializable {

    private Integer id;

    @Min(1)
    @Max(14)
    @NotNull
    private byte nr;

    @Min(0)
    @Max(100)
    private Byte weight;

    @NotNull
    private LessonType type;

    private RightType rightType;

    @Override
    public int compareTo(LessonTemplateDto o) {
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
        if (!(o instanceof LessonTemplateDto)) {
            return false;
        }
        return this.compareTo((LessonTemplateDto) o) == 0;
    }
}
