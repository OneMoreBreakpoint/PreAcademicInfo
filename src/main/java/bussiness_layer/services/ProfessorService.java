package bussiness_layer.services;

import bussiness_layer.dto.*;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService implements IProfessorService {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Autowired
    private ITeachingRepository teachingRepository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private IPartialExamRepository partialExamRepository;

    @Autowired
    private ILessonRepository lessonRepository;


    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourseAndGroup(String profUsername, String courseCode, Short groupCode) {
        if (groupRepository.findByCode(groupCode) == null) {
            throw new ResourceNotFoundException();
        }
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if(teaching == null){
            throw new ResourceNotFoundException();
        }
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if (!teachingDTO.hasSeminarRightsOverGroup(groupCode) && !teachingDTO.hasLaboratoryRightsOverGroup(groupCode)
                && !teachingDTO.hasCoordinatorRights()) {
            throw new ResourceNotFoundException();
        }
        List<Enrollment> enrollments = enrollmentRepository.findByCourseAndGroup(courseCode, groupCode);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }
        List<EnrollmentDTO> enrollmentDTOS = enrollments.stream().map(EnrollmentDTO::new).collect(Collectors.toList());
        stripUnauthorizedReadLessonsAndExams(enrollmentDTOS, teachingDTO, groupCode);
        return enrollmentDTOS;
    }

    @Override
    public TeachingDTO getTeachingByProfessorAndCourse(String profUsername, String courseCode) {
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (teaching == null) {
            throw new ResourceNotFoundException();
        }
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if (teachingDTO.hasCoordinatorRights()) { //coordinator can view all groups
            addAllGroupsToTeachingForCourse(teachingDTO, courseCode);
        }
        return teachingDTO;
    }

    @Override
    public void updateEnrollments(String profUsername, List<EnrollmentDTO> enrollments) {
        List<Enrollment> enrollmentEntities = enrollments.stream().map(EnrollmentDTO::toEntity).collect(Collectors.toList());
        authorizeUpdateLessonsAndExams(enrollmentEntities, profUsername);
        List<Lesson> lessons = enrollmentEntities.stream()
                .map(Enrollment::getLessons)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<PartialExam> partialExams = enrollmentEntities.stream()
                .map(Enrollment::getPartialExams)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        lessonRepository.saveAll(lessons);
        partialExamRepository.saveAll(partialExams);
        lessonRepository.flush();
        partialExamRepository.flush();
    }



    private List<Integer> getLessonIdsFromEnrollments(List<Enrollment> enrollments){
        return enrollments.stream().map(Enrollment::getLessons).flatMap(Collection::stream)
                .map(Lesson::getId).collect(Collectors.toList());
    }

    private List<Integer> getExamIdsFromEnrollments(List<Enrollment> enrollments){
        return enrollments.stream().map(Enrollment::getPartialExams).flatMap(Collection::stream)
                .map(PartialExam::getId).collect(Collectors.toList());
    }

    

    private void addAllGroupsToTeachingForCourse(TeachingDTO teachingDTO, String courseCode){
        Set<GroupDTO> allGroupsTakingThisCourse = groupRepository.findByCourse(courseCode)
                .stream().map(GroupDTO::new).collect(Collectors.toSet());
        teachingDTO.getAllGroups().addAll(allGroupsTakingThisCourse);
    }
}
