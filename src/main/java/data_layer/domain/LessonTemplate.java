package data_layer.domain;

import lombok.*;
import utils.LessonType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "LessonTemplates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(1)
    @Max(14)
    @NotNull
    private byte nr;

    @Min(0)
    @Max(100)
    private Byte weight;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private LessonType type;

    @ManyToOne
    private Course course;
}
