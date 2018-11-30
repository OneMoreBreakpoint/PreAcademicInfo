package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.GroupDTO;
import bussiness_layer.dto.TeachingDTO;
import data_layer.domain.Enrollment;
import data_layer.domain.Teaching;
import data_layer.repositories.IEnrollmentRepository;
import data_layer.repositories.ITeachingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService implements IProfessorService {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Autowired
    private ITeachingRepository teachingRepository;


    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourseAndGroup(String courseCode, Short groupCode) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseAndGroup(courseCode, groupCode);
        return enrollments.stream().map(EnrollmentDTO::new).collect(Collectors.toList());
    }

    @Override
    public SortedSet<GroupDTO> getGroupsByProfessorAndCourse(String profUsername, String courseCode) {
        Teaching teaching = teachingRepository.findOneByProfessorUsernameAndCourseCode(profUsername, courseCode);
        return (new TeachingDTO(teaching)).getGroups();
    }
}
