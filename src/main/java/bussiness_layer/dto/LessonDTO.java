package bussiness_layer.dto;

import data_layer.domain.Lesson;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LessonDTO {

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
    private Lesson.LessonType type;

    private Byte nrOfBonusPoints;

    private boolean readonly;

    public LessonDTO(Lesson entity){
        this.id = entity.getId();
        this.nr = entity.getNr();
        this.grade = entity.getGrade();
        this.type = entity.getType();
        this.nrOfBonusPoints = entity.getNrOfBonusPoints();
        this.attended = entity.isAttended();
    }

    public Lesson toEntity(){
        Lesson entity = new Lesson();
        entity.setId(this.id);
        entity.setNr(this.nr);
        entity.setGrade(this.grade);
        entity.setType(this.type);
        entity.setNrOfBonusPoints(this.nrOfBonusPoints);
        entity.setAttended(this.attended);
        return entity;
    }

    public boolean isReadable(TeachingDTO teaching, Short groupCode){
        return teaching.hasCoordinatorRights() ||
                (this.getType() == Lesson.LessonType.LABORATORY && teaching.hasLaboratoryRightsOverGroup(groupCode))
                || (this.getType() == Lesson.LessonType.SEMINAR && teaching.hasSeminarRightsOverGroup(groupCode));
    }

    public boolean isWritable(TeachingDTO teaching, Short groupCode){
        return (this.getType() == Lesson.LessonType.LABORATORY && teaching.hasLaboratoryRightsOverGroup(groupCode))
                || (this.getType() == Lesson.LessonType.SEMINAR && teaching.hasSeminarRightsOverGroup(groupCode));
    }

    public boolean isReadOnly(TeachingDTO teaching, Short groupCode){
        return this.isReadable(teaching, groupCode) && !this.isWritable(teaching, groupCode);
    }
}
