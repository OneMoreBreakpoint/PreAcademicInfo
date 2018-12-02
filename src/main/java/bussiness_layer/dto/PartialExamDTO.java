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

    private PartialExam.PartialExamType type;

    private boolean readonly;

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

    public boolean isReadable(TeachingDTO teaching, Short groupCode){
        return teaching.hasCoordinatorRights()
                || (this.getType() == PartialExam.PartialExamType.LABORATORY && teaching.hasLaboratoryRightsOverGroup(groupCode))
                || (this.getType() == PartialExam.PartialExamType.SEMINAR && teaching.hasSeminarRightsOverGroup(groupCode));
    }

    public boolean isWritable(TeachingDTO teaching, Short groupCode){
        return (this.getType() == PartialExam.PartialExamType.LABORATORY && teaching.hasLaboratoryRightsOverGroup(groupCode))
                ||(this.getType() == PartialExam.PartialExamType.SEMINAR && teaching.hasSeminarRightsOverGroup(groupCode))
                ||(this.getType() == PartialExam.PartialExamType.COURSE && teaching.hasCoordinatorRights());
    }

    public boolean isReadOnly(TeachingDTO teaching, Short groupCode){
        return this.isReadable(teaching, groupCode) && !this.isWritable(teaching, groupCode);
    }
}
