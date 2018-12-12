package bussiness_layer.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto implements Serializable {

    private Integer id;
    private StudentDto student;
    private List<LessonDto> lessons;
    private CourseDto course;

}
