package bussiness_layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.mappers.ProfessorCourseMapper;
import data_layer.domain.Course;
import data_layer.domain.Group;
import data_layer.domain.ProfessorRight;
import data_layer.repositories.IProfessorRightRepository;

@Component
public class ProfessorHandler {

    //TODO(Norberth) extract from professor service all functions that call professorRightRepository's methods and put them here

    @Autowired
    private final IProfessorRightRepository professorRightRepository;

    public ProfessorHandler(IProfessorRightRepository professorRightRepository) {
        this.professorRightRepository = professorRightRepository;
    }

    public List<ProfessorCourseDto> getProfessorCourseDtoList(String profUsername, List<ProfessorRight> professorRights) {

        List<Course> courses = getCourses(professorRights);

        List<ProfessorCourseDto> professorCourseDtos = new ArrayList<>();

        courses.forEach(course -> {
            List<Group> groups = professorRightRepository.findByProfessorAndCourse(profUsername, course.getCode()).stream()
                    .map(professorRight -> professorRight.getGroup())
                    .distinct()
                    .collect(Collectors.toList());
            professorCourseDtos.add(ProfessorCourseMapper.toProfessorCourseDto(course, groups));
        });

        return professorCourseDtos;
    }

    private static List<Course> getCourses(List<ProfessorRight> professorRights) {
        return professorRights.stream()
                .map(professorRight -> professorRight.getCourse())
                .distinct()
                .collect(Collectors.toList());
    }


}
