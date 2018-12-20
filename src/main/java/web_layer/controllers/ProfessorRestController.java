package web_layer.controllers;

import java.security.Principal;
import java.util.ArrayList;

import bussiness_layer.dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bussiness_layer.dto.LessonDto;
import bussiness_layer.services.IProfessorService;

@RestController
@RequestMapping("/app/professor")
public class ProfessorRestController {

    @Autowired
    private final IProfessorService service;

    public ProfessorRestController(IProfessorService service) {
        this.service = service;
    }

    @PutMapping("/lessons")
    public ResponseEntity<?> putLessons(@RequestBody ArrayList<LessonDto> lessons, Principal crtUser) {
        service.updateLessons(crtUser.getName(), lessons);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getRelatedCourses(Principal crtUser) {
        service.getRelatedCourses(crtUser.getName());
        return ResponseEntity.ok(null);
    }

    @PutMapping("/course")
    public ResponseEntity<?> putCourse(@RequestBody CourseDto course, Principal crtUser){
        service.updateCourse(crtUser.getName(), course);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/course/{courseCode}")
    public CourseDto getCourse(@PathVariable String courseCode,  Principal crtUser){
        return service.getCourse(crtUser.getName(), courseCode);
    }

}
