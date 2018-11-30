package bussiness_layer.dto;

import data_layer.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EnrollmentDTO {

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

    public double getSeminarAttendance(){
        double nrOfAttendances = this.lessons.stream()
                .filter(lesson -> lesson.getType() == Lesson.LessonType.SEMINAR)
                .filter(LessonDTO::isAttended)
                .count();
        int nrOfSeminars = this.course.getNrOfSeminars();
        return nrOfSeminars > 0 ? (nrOfAttendances/nrOfSeminars) * 100 : 100;
    }

    public double getLaboratoryAttendance(){
        double nrOfAttendances = this.lessons.stream()
                .filter(lesson -> lesson.getType() == Lesson.LessonType.LABORATORY)
                .filter(LessonDTO::isAttended)
                .count();
        int nrOfLabs = this.course.getNrOfLaboratories();
        return nrOfLabs > 0 ? (nrOfAttendances/nrOfLabs) * 100 : 100;
    }

    public double getAverageGrade(){
        int sum=0, n=0;
        for(LessonDTO lesson : this.lessons){
            if(lesson.getGrade() != null){
                sum += lesson.getGrade();
                n++;
            }
        }
        return n > 0 ? sum/n : 0.0;
    }

    public boolean hasMinimumAttendance(){
        return this.getSeminarAttendance() >= 75 && this.getLaboratoryAttendance() >= 90;
    }

    public boolean hasMinimumGrade(){
        return this.getAverageGrade() >= 5;
    }

    public int getTotalBonus(){
        return this.lessons.stream()
                .filter(lesson -> lesson.getNrOfBonusPoints() != null)
                .map(lesson -> (int)lesson.getNrOfBonusPoints())
                .reduce((x,y) -> x+y)
                .orElse(0);
    }
}
