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

    public LessonDTO(Lesson entity){
        this.id = entity.getId();
        this.nr = entity.getNr();
        this.grade = entity.getGrade();
        this.type = entity.getType();
        this.nrOfBonusPoints = entity.getNrOfBonusPoints();
    }
}
