package bussiness_layer.dto;

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
    private byte nr;

    @Max(10)
    @Min(1)
    private Byte grade;

    private boolean readonly;

    private PartialExam.PartialExamType type;

}
