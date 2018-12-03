package web_layer.controllers;

import bussiness_layer.dto.EnrollmentDTO;
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
    private IProfessorService service;

    @PutMapping("/enrollments")
    public ResponseEntity<?> putEnrollments(@RequestBody ArrayList<EnrollmentDTO> enrollments, Principal crtUser){
        service.updateEnrollments(crtUser.getName(), enrollments);
        return ResponseEntity.ok(null);
    }
}
