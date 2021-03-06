package web_layer.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import bussiness_layer.dto.*;
import bussiness_layer.services.IEnrollmentService;
import bussiness_layer.utils.ApachePOIExcelWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bussiness_layer.services.ICourseService;
import bussiness_layer.services.IProfessorService;
import utils.EmailSender;

@RestController
@RequestMapping("/app/professor")
@ComponentScan(basePackages = "utils")
public class ProfessorRestController {

    @Autowired
    private final IProfessorService professorService;

    @Autowired
    private final ICourseService courseService;

    @Autowired
    private final IEnrollmentService enrollmentService;

    @Autowired
    private final EmailSender mailSender;

    @Autowired
    public ProfessorRestController(IProfessorService professorService, ICourseService courseService,
                                   IEnrollmentService enrollmentService, EmailSender mailSender) {
        this.professorService = professorService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.mailSender = mailSender;
    }

    @PutMapping("/lessons")
    public ResponseEntity<?> putLessons(@RequestBody ArrayList<LessonDto> lessons, Principal crtUser) {
        List<EmailNotificationDto> emailNotificationDtos = professorService.updateLessons(crtUser.getName(), lessons);

        mailSender.sendEmail(emailNotificationDtos);

        return ResponseEntity.ok(null);
    }

    @PutMapping("/")
    public ResponseEntity<?> put(@RequestBody ProfessorDto professorDto, Principal crtUser) {
        professorService.updateProfessor(professorDto, crtUser.getName());
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

    @GetMapping(value = "/export", produces = "text/plain")
    public String doExport(@RequestParam boolean publicType, @RequestParam String course,
                                      @RequestParam String group, Principal crtUser) {
        return enrollmentService.generateExcelExportFile(crtUser.getName(), course, group, publicType);
    }

}
