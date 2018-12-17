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
import utils.RightType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto implements Comparable<LessonDto>, Serializable {

    private static final long serialVersionUID = 2024L;

    private Integer id;

    @NotNull
    private boolean attended;

    @Max(10)
    @Min(1)
    private Byte grade;

    @Min(-10)
    @Max(10)
    private Byte bonus;

    @NotNull
    private LessonTemplateDto template;

    @Override
    public int compareTo(LessonDto o) {
        return this.template.compareTo(o.template);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LessonDto)){
            return false;
        }
        LessonDto that = (LessonDto) obj;
        return this.template.equals(that.template);
    }
}
