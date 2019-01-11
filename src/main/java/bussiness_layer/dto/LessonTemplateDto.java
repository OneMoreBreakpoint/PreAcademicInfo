package bussiness_layer.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private RightType rightType;

    @NotNull
    private String courseName;

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
