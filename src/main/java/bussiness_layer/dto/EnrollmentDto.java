package bussiness_layer.dto;

import java.io.Serializable;
import java.util.List;

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
public class EnrollmentDto implements Serializable {

    private static final long serialVersionUID = 2025L;

    private Integer id;
    private StudentDto student;
    private List<LessonDto> lessons;
    private CourseDto course;

}
