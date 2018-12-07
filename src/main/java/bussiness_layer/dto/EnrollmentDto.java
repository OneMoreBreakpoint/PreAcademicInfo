package bussiness_layer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class EnrollmentDto implements Serializable {

    private Integer id;
    private StudentDto student;
    private List<PartialExamDto> partialExams;
    private List<LessonDto> lessons;
    private CourseDto course;

}
