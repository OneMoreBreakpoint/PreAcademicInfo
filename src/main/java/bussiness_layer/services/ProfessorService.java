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

    private void stripUnauthorizedReadLessonsAndExams(List<EnrollmentDTO> enrollments, TeachingDTO teaching, Short groupCode) {
        enrollments.forEach(enrollment -> {
            //keep only readable lessons and exams
            List<LessonDTO> authLessons = enrollment.getLessons().stream()
                    .filter(lessonDTO -> lessonDTO.isReadable(teaching, groupCode))
                    .collect(Collectors.toList());
            List<PartialExamDTO> authExams = enrollment.getPartialExams().stream()
                    .filter(exam -> exam.isReadable(teaching, groupCode))
                    .collect(Collectors.toList());
            //mark lessons and exams as readonly if it is the case
            authLessons.forEach(lessonDTO -> lessonDTO.setReadonly(lessonDTO.isReadOnly(teaching, groupCode)));
            authExams.forEach(examDTO -> examDTO.setReadonly(examDTO.isReadOnly(teaching, groupCode)));
            enrollment.setLessons(authLessons);
            enrollment.setPartialExams(authExams);
        });
    }

    private Set<Integer> getAuthorizedLessonIds(Collection<Enrollment> authEnrollments, TeachingDTO teachingDTO){
        Set<Integer> authLessonsIds = new HashSet<>();
        for (Enrollment authEnrl : authEnrollments) {
            Short groupCode = authEnrl.getStudent().getGroup().getCode();
            authLessonsIds.addAll(authEnrl.getLessons().stream()
                    .map(LessonDTO::new)
                    .filter(lesson ->lesson.isWritable(teachingDTO, groupCode))
                    .map(LessonDTO::getId).collect(Collectors.toSet()));
        }
        return authLessonsIds;
    }

    private Set<Integer> getAuthorizedExamIds(Collection<Enrollment> authEnrollments, TeachingDTO teachingDTO){
        Set<Integer> authExamsIds = new HashSet<>();
        for (Enrollment authEnrl : authEnrollments) {
            Short groupCode = authEnrl.getStudent().getGroup().getCode();
            authExamsIds.addAll(authEnrl.getPartialExams().stream()
                    .map(PartialExamDTO::new)
                    .filter(exam -> exam.isWritable(teachingDTO, groupCode))
                    .map(PartialExamDTO::getId).collect(Collectors.toSet()));
        }
        return authExamsIds;
    }

    private List<Integer> getLessonIdsFromEnrollments(List<Enrollment> enrollments){
        return enrollments.stream().map(Enrollment::getLessons).flatMap(Collection::stream)
                .map(Lesson::getId).collect(Collectors.toList());
    }

    private List<Integer> getExamIdsFromEnrollments(List<Enrollment> enrollments){
        return enrollments.stream().map(Enrollment::getPartialExams).flatMap(Collection::stream)
                .map(PartialExam::getId).collect(Collectors.toList());
    }

    private void authorizeUpdateLessonsAndExams(List<Enrollment> enrollmentsNeedingUpdate, String profUsername) {
        if (enrollmentsNeedingUpdate.size() == 0) {
            return;
        }
        String courseCode = enrollmentsNeedingUpdate.get(0).getCourse().getCode();
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (teaching == null) {
            throw new AccessForbiddenException();
        }
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if (teachingDTO.hasCoordinatorRights()) {
            Collection<GroupDTO> allGroups = groupRepository.findByCourse(courseCode).stream()
                    .map(GroupDTO::new).collect(Collectors.toList());
            teachingDTO.getAllGroups().addAll(allGroups);
        }
        Set<Short> allGroupCodes = teachingDTO.getAllGroups().stream().map(GroupDTO::getCode).collect(Collectors.toSet());
        List<Enrollment> allEnrollments = enrollmentRepository.findByCourseAndGroups(courseCode, allGroupCodes);
        Set<Integer> authLessonsIds = getAuthorizedLessonIds(allEnrollments, teachingDTO);
        Set<Integer> authExamsIds = getAuthorizedExamIds(allEnrollments, teachingDTO);
        List<Integer> toBeUpdatedLessonsIds = getLessonIdsFromEnrollments(enrollmentsNeedingUpdate);
        List<Integer> toBeUpdatedExamsIds = getExamIdsFromEnrollments(enrollmentsNeedingUpdate);
        for (Integer id : toBeUpdatedLessonsIds) {
            if (!authLessonsIds.contains(id)) {
                throw new AccessForbiddenException();
            }
        }
        for (Integer id : toBeUpdatedExamsIds) {
            if (!authExamsIds.contains(id)) {
                throw new AccessForbiddenException();
            }
        }
    }
}
