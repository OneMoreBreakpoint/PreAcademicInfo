package factory;

import bussiness_layer.dto.CourseDto;
import data_layer.domain.Course;

public class CourseFactory {
    public static Course.CourseBuilder generateCourseBuilder() {
        return Course.builder()
                .code("MLR0000")
//                .coordinator(ProfessorFactory.generateProfessor())
                .name("LFTC")
                .nrOfLaboratories((byte) 14)
                .nrOfSeminars((byte) 14);
    }

    public static Course generateCourse() {
        return generateCourseBuilder().build();
    }

    public static CourseDto.CourseDtoBuilder generateCourseDtoBuilder() {
        return CourseDto.builder()
                .code("MLR0000")
//                .coordinator(ProfessorFactory.generateProfessorDto())
                .name("LFTC")
                .nrOfLaboratories((byte) 14)
                .nrOfSeminars((byte) 14);
    }

    public static CourseDto generateCourseDto() {
        return generateCourseDtoBuilder().build();
    }
}
