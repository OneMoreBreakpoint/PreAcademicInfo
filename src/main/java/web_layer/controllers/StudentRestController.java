package web_layer.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bussiness_layer.dto.StudentDto;
import bussiness_layer.services.IStudentService;

@RestController
@RequestMapping("/app/student")
public class StudentRestController {

    @Autowired
    private IStudentService service;

    @PutMapping("/")
    public ResponseEntity<?> put(@RequestBody StudentDto studentDto, Principal crtUser){
        service.updateStudent(studentDto, crtUser.getName());
        return ResponseEntity.ok(null);
    }
}
