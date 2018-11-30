package bussiness_layer.dto;

import data_layer.domain.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CourseDTO {
    private Integer id;

    @NotNull
    @Size(max = 7)
    private String code;

    @NotNull
    private String name;

    @NotNull
    @Max(14)
    @Min(0)
    private Byte nrOfSeminars;

    @NotNull
    @Max(14)
    @Min(7)
    private Byte nrOfLaboratories;

    public CourseDTO(Course entity){
        this.id = entity.getId();
        this.code = entity.getCode();
        this.nrOfSeminars = entity.getNrOfSeminars();
        this.nrOfLaboratories = entity.getNrOfLaboratories();
    }
}
