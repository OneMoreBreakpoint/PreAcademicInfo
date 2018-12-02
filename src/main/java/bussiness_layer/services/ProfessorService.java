package bussiness_layer.services;

import bussiness_layer.dto.*;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.exceptions.AccessForbiddenException;
import utils.handlers.TeachingHandler;
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
        stripUnauthorizedReadLessonsAndExams(enrollmentDTOS, teaching, groupCode);
        return enrollmentDTOS;
    }

    @Override
    public TeachingDTO getTeachingByProfessorAndCourse(String profUsername, String courseCode) {
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (teaching == null) {
            throw new ResourceNotFoundException();
        }
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if (TeachingHandler.professorHasCoordinatorRights(teachingDTO)) { //coordinator can view all groups
            Set<GroupDTO> allGroupsTakingThisCourse = groupRepository.findByCourse(courseCode).stream()
                    .map(GroupDTO::new).collect(Collectors.toSet());
            teachingDTO.getAllGroups().addAll(allGroupsTakingThisCourse);
        }
        return teachingDTO;
    }

    @Override
    public void updateEnrollments(String profUsername, List<EnrollmentDTO> enrollments) {
        List<Enrollment> enrollmentEntities = enrollments.stream()
                .map(EnrollmentDTO::toEntity)
                .collect(Collectors.toList());
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

    private void stripUnauthorizedReadLessonsAndExams(List<EnrollmentDTO> enrollments, Teaching teaching, Short groupCode) {
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
                                    || TeachingHandler.professorHasCoordinatorRights(teaching)) //TODO: check if it works (should add COURSE?)
                    .collect(Collectors.toList());
            enrollment.setLessons(authLessons);
            enrollment.setPartialExams(authExams);
        });
    }

    private void authorizeUpdateLessonsAndExams(List<Enrollment> enrollmentsNeedingUpdate, String profUsername){
        if(enrollmentsNeedingUpdate.size() == 0){
            return;
        }
        String courseCode = enrollmentsNeedingUpdate.get(0).getCourse().getCode();
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if(teaching == null){
            throw new AccessForbiddenException();
        }
        Set<Group> allGroups = new HashSet<>();
        if(TeachingHandler.professorHasCoordinatorRights(teaching)){
            allGroups.addAll(groupRepository.findByCourse(courseCode));
        }else{
            allGroups.addAll(teaching.getLaboratoryGroups());
            allGroups.addAll(teaching.getSeminarGroups());
        }
        Set<Short> allGroupCodes = allGroups.stream().map(Group::getCode).collect(Collectors.toSet());
        List<Enrollment> authEnrollments = enrollmentRepository.findByCourseAndGroups(courseCode, allGroupCodes);
        Set<Integer> authLessonsIds = new HashSet<>();
        Set<Integer> authExamsIds = new HashSet<>();
        for(Enrollment authEnrl : authEnrollments){
            authLessonsIds.addAll( authEnrl.getLessons().stream().filter(lesson->{
                Short groupCode = authEnrl.getStudent().getGroup().getCode();
                return (lesson.getType() == Lesson.LessonType.LABORATORY && TeachingHandler.professorHasLaboratoryRightsOverGroup(teaching, groupCode))
                        || (lesson.getType() == Lesson.LessonType.SEMINAR && TeachingHandler.professorHasSeminarRightsOverGroup(teaching, groupCode));
            }).map(Lesson::getId).collect(Collectors.toSet()) );
            authExamsIds.addAll( authEnrl.getPartialExams().stream().filter(exam->{
                Short groupCode = authEnrl.getStudent().getGroup().getCode();
                return (exam.getType() == PartialExam.PartialExamType.LABORATORY && TeachingHandler.professorHasLaboratoryRightsOverGroup(teaching, groupCode))
                        || (exam.getType() == PartialExam.PartialExamType.SEMINAR && TeachingHandler.professorHasSeminarRightsOverGroup(teaching, groupCode))
                        || (exam.getType() == PartialExam.PartialExamType.COURSE && TeachingHandler.professorHasCoordinatorRights(teaching));
            }).map(PartialExam::getId).collect(Collectors.toSet()) );
        }
        List<Integer> toBeUpdatedLessonsIds = enrollmentsNeedingUpdate.stream()
                .map(Enrollment::getLessons).flatMap(Collection::stream)
                .map(Lesson::getId).collect(Collectors.toList());
        List<Integer> toBeUpdatedExamsIds = enrollmentsNeedingUpdate.stream()
                .map(Enrollment::getPartialExams).flatMap(Collection::stream)
                .map(PartialExam::getId).collect(Collectors.toList());
        for(Integer id : toBeUpdatedLessonsIds){
            if(!authLessonsIds.contains(id)){
                throw new AccessForbiddenException();
            }
        }
        for(Integer id : toBeUpdatedExamsIds){
            if(!authExamsIds.contains(id)){
                throw new AccessForbiddenException();
            }
        }
    }
}
