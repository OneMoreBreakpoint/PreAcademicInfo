package data_layer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Lessons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson implements Comparable<Lesson> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean attended;

    @Max(10)
    @Min(0)
    private Byte grade;

    @Min(-10)
    @Max(10)
    private Byte bonus;

    @NotNull
    @ManyToOne
    private Enrollment enrollment;

    @NotNull
    @ManyToOne
    private LessonTemplate template;

    @Override
    public int compareTo(Lesson o) {
        return this.id.compareTo(o.id);
    }
}
