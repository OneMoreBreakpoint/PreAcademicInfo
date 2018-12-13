package web_layer.controllers;

import bussiness_layer.dto.LessonDto;
import bussiness_layer.services.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

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
}
