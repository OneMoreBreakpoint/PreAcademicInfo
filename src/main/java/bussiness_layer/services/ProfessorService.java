package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDTO;
import bussiness_layer.dto.LessonDTO;
import bussiness_layer.dto.PartialExamDTO;
import bussiness_layer.dto.TeachingDTO;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.handlers.TeachingHandler;
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
        if (groupRepository.findByCode(groupCode) == null) {
            throw new ResourceNotFoundException();
        }
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if ((teaching == null) || (!TeachingHandler.professorHasSeminarRightsOverGroup(teaching, groupCode)
                && !TeachingHandler.professorHasLaboratoryRightsOverGroup(teaching, groupCode)
                && !TeachingHandler.professorHasCoordinatorRights(teaching))) {
            throw new ResourceNotFoundException();
        }
        List<Enrollment> enrollments = enrollmentRepository.findByCourseAndGroup(courseCode, groupCode);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }
        List<EnrollmentDTO> enrollmentDTOS = enrollments.stream().map(EnrollmentDTO::new).collect(Collectors.toList());
        stripUnauthorizedLessonsAndExams(enrollmentDTOS, teaching, groupCode);
        return enrollmentDTOS;
    }

    @Override
    public TeachingDTO getTeachingByProfessorAndCourse(String profUsername, String courseCode) {
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (teaching == null) {
            throw new ResourceNotFoundException();
        }
        return new TeachingDTO(teaching);
    }

    private void stripUnauthorizedLessonsAndExams(List<EnrollmentDTO> enrollments, Teaching teaching, Short groupCode) {
        enrollments.forEach(enrollment -> {
            List<LessonDTO> authLessons = enrollment.getLessons().stream()
                    .filter(lesson ->
                            (lesson.getType() == Lesson.LessonType.LABORATORY && TeachingHandler.professorHasLaboratoryRightsOverGroup(teaching, groupCode))
                                    || (lesson.getType() == Lesson.LessonType.SEMINAR && TeachingHandler.professorHasSeminarRightsOverGroup(teaching, groupCode))
                                    || TeachingHandler.professorHasCoordinatorRights(teaching))
                    .collect(Collectors.toList());
            List<PartialExamDTO> authExams = enrollment.getPartialExams().stream()
                    .filter(exam ->
                            (exam.getType() == PartialExam.PartialExamType.LABORATORY && TeachingHandler.professorHasLaboratoryRightsOverGroup(teaching, groupCode))
                                    || (exam.getType() == PartialExam.PartialExamType.SEMINAR && TeachingHandler.professorHasSeminarRightsOverGroup(teaching, groupCode))
                                    || TeachingHandler.professorHasCoordinatorRights(teaching))
                    .collect(Collectors.toList());
            enrollment.setLessons(authLessons);
            enrollment.setPartialExams(authExams);
        });
    }

}
