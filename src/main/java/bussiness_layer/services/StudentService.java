package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.mappers.EnrollmentMapper;
import data_layer.domain.Enrollment;
import data_layer.domain.Professor;
import data_layer.domain.Student;
import data_layer.repositories.IEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data_layer.repositories.IStudentRepository;
import utils.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public List<EnrollmentDto> getEnrollments(String studUsername) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }
        return EnrollmentMapper.toDtoList(enrollments);
    }

    @Override
    public void updateStudent(StudentDto studentDto)
    {
        Student student = studentRepository.findByUsername(studentDto.getUsername());
        if (student == null)
            throw  new ResourceNotFoundException();
        student.setNotifiedByEmail(studentDto.isNotifiedByEmail());
        if (studentDto.getPassword() != null)
            student.setEncryptedPassword(BCrypt.hashpw(studentDto.getPassword(), BCrypt.gensalt()));
        student.setPathToProfilePhoto(studentDto.getPathToProfilePhoto());
        studentRepository.flush();
    }

}
