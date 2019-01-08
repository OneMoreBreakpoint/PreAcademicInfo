package bussiness_layer.services.impl;

import bussiness_layer.services.IStudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService implements IStudentService {

//    @Override
//    public List<EnrollmentDto> getEnrollments(String studUsername) {
//        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
//        if (enrollments.size() == 0) {
//            throw new ResourceNotFoundException();
//        }
//        return EnrollmentMapper.toDtoList(enrollments);
//    }

}
