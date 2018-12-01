package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.TeachingDTO;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.TeachingHandler;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService implements IProfessorService {

    @Autowired
    private IProfessorRepository professorRepository;

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Autowired
    private ITeachingRepository teachingRepository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private ICourseRepository courseRepository;


    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourseAndGroup(String profUsername, String courseCode, Short groupCode) {
        if(groupRepository.findByCode(groupCode) == null){
            throw new ResourceNotFoundException();
        }
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if((teaching == null) || !TeachingHandler.professorHasAnyRightsOverGroup(teaching, groupCode)){
            throw new ResourceNotFoundException();
        }
        List<Enrollment> enrollments = enrollmentRepository.findByCourseAndGroup(courseCode, groupCode);
        if(enrollments.size()==0){
            throw new ResourceNotFoundException();
        }
        return enrollments.stream().map(EnrollmentDTO::new).collect(Collectors.toList());
    }

    @Override
    public TeachingDTO getTeachingByProfessorAndCourse(String profUsername, String courseCode){
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if(teaching == null){
           throw new ResourceNotFoundException();
        }
        return new TeachingDTO(teaching);
    }


}
