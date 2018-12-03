package bussiness_layer.dto;

import data_layer.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EnrollmentDTO implements Serializable {

    private Integer id;
    private StudentDTO student;
    private List<PartialExamDTO> partialExams;
    private List<LessonDTO> lessons;
    private CourseDTO course;


    public EnrollmentDTO(Enrollment entity){
        this.id = entity.getId();
        this.student = new StudentDTO(entity.getStudent());
        this.partialExams = entity.getPartialExams().stream().map(PartialExamDTO::new).collect(Collectors.toList());
        this.lessons = entity.getLessons().stream().map(LessonDTO::new).collect(Collectors.toList());
        this.course = new CourseDTO(entity.getCourse());
    }

    public Enrollment toEntity(){
        Enrollment entity = new Enrollment();
        entity.setId(this.id);
        entity.setStudent(this.student.toEntity());
        entity.setPartialExams(this.partialExams.stream().map(partialExamDTO -> partialExamDTO.toEntity()).collect(Collectors.toList()));
        entity.setLessons(this.lessons.stream().map(lessonDTO -> lessonDTO.toEntity()).collect(Collectors.toList()));
        entity.setCourse(this.course.toEntity());
        return entity;
    }
}
