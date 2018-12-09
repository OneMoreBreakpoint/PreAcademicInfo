package bussiness_layer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.mappers.EnrollmentMapper;
import data_layer.domain.Enrollment;
import data_layer.repositories.IEnrollmentRepository;
import utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;


    @Override
    public List<EnrollmentDto> getEnrollments(String studUsername) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }
        List<EnrollmentDto> enrollmentDTOS = EnrollmentMapper.toEntityDtoList(enrollments);
        return enrollmentDTOS;
    }
}
