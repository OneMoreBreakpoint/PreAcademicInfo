package bussiness_layer.dto;

import data_layer.domain.PartialExam;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class PartialExamDTO {

    private Integer id;

    @Min(1)
    @Max(4)
    private byte nr;

    @Max(10)
    @Min(1)
    private Byte grade;

    private boolean readonly;

    private PartialExam.PartialExamType type;

    public PartialExamDTO(PartialExam entity){
        this.id = entity.getId();
        this.nr = entity.getNr();
        this.grade = entity.getGrade();
        this.type = entity.getType();
    }

    public PartialExam toEntity(){
        PartialExam entity = new PartialExam();
        entity.setId(this.id);
        entity.setNr(this.nr);
        entity.setGrade(this.grade);
        entity.setType(this.type);
        return entity;
    }




}
