package bussiness_layer.services;

import bussiness_layer.dto.*;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.handlers.TeachingHandler;
import utils.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if(TeachingHandler.professorHasCoordinatorRights(teachingDTO)){ //coordinator can view all groups
            Set<GroupDTO> allGroupsTakingThisCourse = groupRepository.findByCourse(courseCode).stream()
                    .map(GroupDTO::new).collect(Collectors.toSet());
            teachingDTO.getAllGroups().addAll(allGroupsTakingThisCourse);
        }
        return teachingDTO;
    }

    @Override
    public void updateEnrollments(List<EnrollmentDTO> enrollments) {
        List<Enrollment> enrollmentEntities = enrollments.stream()
                .map(EnrollmentDTO::toEntity)
                .collect(Collectors.toList());
        List<Lesson> lessons = enrollmentEntities.stream()
                .map(Enrollment::getLessons)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<PartialExam> partialExams = enrollmentEntities.stream()
                .map(Enrollment::getPartialExams)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println(lessons);
        System.out.println(partialExams);
        lessonRepository.saveAll(lessons);
        partialExamRepository.saveAll(partialExams);
        lessonRepository.flush();
        partialExamRepository.flush();
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
