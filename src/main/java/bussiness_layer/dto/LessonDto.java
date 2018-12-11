package bussiness_layer.dto;

import java.util.Objects;

import data_layer.domain.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

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

    private Byte bonus;

    private boolean readonly;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LessonDto lessonDto = (LessonDto) o;
        return Objects.equals(nr, lessonDto.nr) &&
                type == lessonDto.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nr, type);
    }
}
