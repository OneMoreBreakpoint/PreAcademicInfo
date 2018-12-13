package bussiness_layer.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto implements Serializable {

    public static final long serialVersionUID = 2023L;

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

    //TODO: @not null
    private ProfessorDto coordinator;

}
