package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.StudentDto;
import bussiness_layer.mappers.EnrollmentMapper;
import data_layer.domain.Enrollment;
import data_layer.domain.Student;
import data_layer.repositories.IEnrollmentRepository;
import data_layer.repositories.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void updateStudent(StudentDto studentDto, String studUsername) {
        Student student = studentRepository.findByUsername(studUsername)
                .orElseThrow(ResourceNotFoundException::new);
        student.setNotifiedByEmail(studentDto.isNotifiedByEmail());
        student.setEncryptedPassword(BCrypt.hashpw(studentDto.getPassword(), BCrypt.gensalt()));
        student.setProfilePhoto(studentDto.getProfilePhoto());
        studentRepository.flush();
    }

}
