package bussiness_layer.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

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

    //TODO: @not null
    private ProfessorDto coordinator;

    private List<LessonTemplateDto> lessonTemplates;

}
