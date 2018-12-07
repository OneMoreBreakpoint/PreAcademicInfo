package web_layer.controllers;

import bussiness_layer.dto.EnrollmentDto;
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
    private IProfessorService service;

    @PutMapping("/enrollments")
    public ResponseEntity<?> putEnrollments(@RequestBody ArrayList<EnrollmentDto> enrollments, Principal crtUser) {
        service.updateEnrollments(crtUser.getName(), enrollments);
        return ResponseEntity.ok(null);
    }
}
