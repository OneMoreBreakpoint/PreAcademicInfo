package web_layer.controllers;

import bussiness_layer.dto.CourseDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.services.ICourseService;
import bussiness_layer.services.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/app/professor")
public class ProfessorRestController {

    @Autowired
    private final IProfessorService professorService;

    @Autowired
    private final ICourseService courseService;

    public ProfessorRestController(IProfessorService professorService, ICourseService courseService) {
        this.professorService = professorService;
        this.courseService = courseService;
    }

    @PutMapping("/lessons")
    public ResponseEntity<?> putLessons(@RequestBody ArrayList<LessonDto> lessons, Principal crtUser) {
        professorService.updateLessons(crtUser.getName(), lessons);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getRelatedCourses(Principal crtUser) {
        professorService.getRelatedCourses(crtUser.getName());
        return ResponseEntity.ok(null);
    }

    @PutMapping("/course")
    public ResponseEntity<?> putCourse(@RequestBody CourseDto course, Principal crtUser) {
        courseService.updateCourse(crtUser.getName(), course);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/course/{courseCode}")
    public CourseDto getCourse(@PathVariable String courseCode, Principal crtUser) {
        return courseService.getCourse(crtUser.getName(), courseCode);
    }

}
