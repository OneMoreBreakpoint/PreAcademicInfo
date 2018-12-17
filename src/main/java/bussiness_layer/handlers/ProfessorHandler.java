package bussiness_layer.handlers;

import bussiness_layer.dto.ProfessorCourseDto;
import bussiness_layer.mappers.ProfessorCourseMapper;
import data_layer.domain.Course;
import data_layer.domain.Group;
import data_layer.domain.ProfessorRight;
import data_layer.repositories.ILessonRepository;
import data_layer.repositories.IProfessorRightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            boolean profIsCoordinator = course.getCoordinator().getUsername().equals(profUsername);
            professorCourseDtos.add(ProfessorCourseMapper.toDto(course, groups, profIsCoordinator));
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
