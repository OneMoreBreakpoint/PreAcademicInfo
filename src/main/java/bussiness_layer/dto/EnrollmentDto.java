package bussiness_layer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto implements Serializable {

    private Integer id;
    private StudentDto student;
    private List<PartialExamDto> partialExams;
    private List<LessonDto> lessons;
    private CourseDto course;

}
