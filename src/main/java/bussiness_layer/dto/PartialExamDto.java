package bussiness_layer.dto;

import java.util.Objects;

import data_layer.domain.PartialExam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartialExamDto {

    private Integer id;

    @Min(1)
    @Max(4)
    private Byte nr;

    @Max(10)
    @Min(1)
    private Byte grade;

    private boolean readonly;

    private PartialExam.PartialExamType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PartialExamDto that = (PartialExamDto) o;
        return nr.equals(that.nr) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nr, type);
    }
}
