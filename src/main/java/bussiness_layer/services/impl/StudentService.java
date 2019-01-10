package bussiness_layer.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bussiness_layer.dto.StudentDto;
import bussiness_layer.services.IStudentService;
import data_layer.domain.Student;
import data_layer.repositories.IStudentRepository;
import utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public void updateStudent(StudentDto studentDto, String studUsername) {
        Student student = studentRepository.findByUsername(studUsername)
                .orElseThrow(ResourceNotFoundException::new);
        student.setNotifiedByEmail(studentDto.isNotifiedByEmail());
        student.setProfilePhoto(studentDto.getProfilePhoto());
        studentRepository.flush();
    }

}
