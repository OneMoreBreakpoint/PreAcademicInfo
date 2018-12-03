package bussiness_layer.utils;

import bussiness_layer.dto.*;
import data_layer.domain.Enrollment;
import data_layer.domain.Lesson;
import data_layer.domain.PartialExam;
import data_layer.domain.Teaching;
import data_layer.repositories.ILessonRepository;
import data_layer.repositories.IPartialExamRepository;
import data_layer.repositories.ITeachingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.exceptions.AccessForbiddenException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Authorizer {

    @Autowired
    private ILessonRepository lessonRepository;

    @Autowired
    private IPartialExamRepository examRepository;

    @Autowired
    private ITeachingRepository teachingRepository;

    public boolean teachingCanReadLesson(TeachingDTO teaching, Short groupCode, LessonDTO lesson){
        return teachingHasCoordinatorRights(teaching) ||
                (lesson.getType() == Lesson.LessonType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                || (lesson.getType() == Lesson.LessonType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode));
    }

    public boolean teachingCanWriteLesson(TeachingDTO teaching, Short groupCode, LessonDTO lesson){
        return (lesson.getType() == Lesson.LessonType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                || (lesson.getType() == Lesson.LessonType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode));
    }

    public boolean teachingCanOnlyReadLesson(TeachingDTO teaching, Short groupCode, LessonDTO lesson){
        return teachingCanReadLesson(teaching, groupCode, lesson) && !teachingCanWriteLesson(teaching, groupCode, lesson);
    }

    public boolean teachingCanReadExam(TeachingDTO teaching, Short groupCode, PartialExamDTO exam){
        return teachingHasCoordinatorRights(teaching)
                || (exam.getType() == PartialExam.PartialExamType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                || (exam.getType() == PartialExam.PartialExamType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode));
    }

    public boolean teachingCanWriteExam(TeachingDTO teaching, Short groupCode, PartialExamDTO exam){
        return (exam.getType() == PartialExam.PartialExamType.LABORATORY && teachingHasLabRightsOverGroup(teaching, groupCode))
                ||(exam.getType() == PartialExam.PartialExamType.SEMINAR && teachingHasSemRightsOverGroup(teaching, groupCode))
                ||(exam.getType() == PartialExam.PartialExamType.COURSE && teachingHasCoordinatorRights(teaching));
    }

    public boolean teachingCanOnlyReadExam(TeachingDTO teaching, Short groupCode, PartialExamDTO exam){
        return teachingCanReadExam(teaching, groupCode, exam) && !teachingCanWriteExam(teaching, groupCode, exam);
    }

    public boolean teachingHasSemRightsOverGroup(TeachingDTO teaching, Short groupCode){
        return teaching.getSeminarGroups().contains(new GroupDTO(groupCode));
    }

    public boolean teachingHasLabRightsOverGroup(TeachingDTO teaching, Short groupCode){
        return teaching.getLaboratoryGroups().contains(new GroupDTO(groupCode));
    }

    public boolean teachingHasCoordinatorRights(TeachingDTO teachingDTO){
        return teachingDTO.getCourse().getCoordinator().equals(teachingDTO.getProfessor());
    }

    public void stripUnauthorizedReadLessonsAndExams(List<EnrollmentDTO> enrollments, TeachingDTO teaching, Short groupCode) {
        enrollments.forEach(enrollment -> {
            //keep only readable lessons and exams
            List<LessonDTO> authLessons = enrollment.getLessons().stream()
                    .filter(lesson -> teachingCanReadLesson(teaching, groupCode, lesson))
                    .collect(Collectors.toList());
            List<PartialExamDTO> authExams = enrollment.getPartialExams().stream()
                    .filter(exam -> teachingCanReadExam(teaching, groupCode, exam))
                    .collect(Collectors.toList());
            authLessons.forEach(lessonDTO -> lessonDTO.setReadonly(teachingCanOnlyReadLesson(teaching, groupCode, lessonDTO)));
            authExams.forEach(examDTO -> examDTO.setReadonly(teachingCanOnlyReadExam(teaching,groupCode, examDTO)));
            enrollment.setLessons(authLessons);
            enrollment.setPartialExams(authExams);
        });
    }

    private Set<Integer> getAuthorizedLessonIds(Collection<EnrollmentDTO> authEnrollments, TeachingDTO teaching){
        Set<Integer> authLessonsIds = new HashSet<>();
        for (EnrollmentDTO authEnrl : authEnrollments) {
            Short groupCode = authEnrl.getStudent().getGroup().getCode();
            authLessonsIds.addAll(authEnrl.getLessons().stream()
                    .filter(lesson ->teachingCanWriteLesson(teaching, groupCode, lesson))
                    .map(LessonDTO::getId).collect(Collectors.toSet()));
        }
        return authLessonsIds;
    }

    private Set<Integer> getAuthorizedExamIds(Collection<EnrollmentDTO> authEnrollments, TeachingDTO teaching){
        Set<Integer> authExamsIds = new HashSet<>();
        for (EnrollmentDTO authEnrl : authEnrollments) {
            Short groupCode = authEnrl.getStudent().getGroup().getCode();
            authExamsIds.addAll(authEnrl.getPartialExams().stream()
                    .filter(exam -> teachingCanWriteExam(teaching, groupCode, exam))
                    .map(PartialExamDTO::getId).collect(Collectors.toSet()));
        }
        return authExamsIds;
    }

    private void authorizeUpdateLessons(List<Lesson> lessonsNeedingUpdate, String profUsername) {
        if (lessonsNeedingUpdate.size() == 0) {
            return;
        }
        String courseCode = lessonsNeedingUpdate.get(0).getEnrollment().getCourse().getCode();
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (teaching == null) {
            throw new AccessForbiddenException();
        }
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if (teachingDTO.hasCoordinatorRights()) {
            addAllGroupsToTeachingForCourse(teachingDTO, courseCode);
        }
        Set<Short> allGroupCodes = teachingDTO.getAllGroups().stream().map(GroupDTO::getCode).collect(Collectors.toSet());
        List<EnrollmentDTO> allEnrollments = enrollmentRepository.findByCourseAndGroups(courseCode, allGroupCodes)
                .stream().map(EnrollmentDTO::new).collect(Collectors.toList());
        Set<Integer> authLessonsIds = getAuthorizedLessonIds(allEnrollments, teachingDTO);
        Set<Integer> authExamsIds = getAuthorizedExamIds(allEnrollments, teachingDTO);
        List<Integer> toBeUpdatedLessonsIds = getLessonIdsFromEnrollments(lessonsNeedingUpdate);
        List<Integer> toBeUpdatedExamsIds = getExamIdsFromEnrollments(lessonsNeedingUpdate);
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

//    private void authorizeUpdateLessonsAndExams(List<Enrollment> enrollmentsNeedingUpdate, String profUsername) {
//        if (enrollmentsNeedingUpdate.size() == 0) {
//            return;
//        }
//        String courseCode = enrollmentsNeedingUpdate.get(0).getCourse().getCode();
//        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
//        if (teaching == null) {
//            throw new AccessForbiddenException();
//        }
//        TeachingDTO teachingDTO = new TeachingDTO(teaching);
//        if (teachingDTO.hasCoordinatorRights()) {
//            addAllGroupsToTeachingForCourse(teachingDTO, courseCode);
//        }
//        Set<Short> allGroupCodes = teachingDTO.getAllGroups().stream().map(GroupDTO::getCode).collect(Collectors.toSet());
//        List<EnrollmentDTO> allEnrollments = enrollmentRepository.findByCourseAndGroups(courseCode, allGroupCodes)
//                .stream().map(EnrollmentDTO::new).collect(Collectors.toList());
//        Set<Integer> authLessonsIds = getAuthorizedLessonIds(allEnrollments, teachingDTO);
//        Set<Integer> authExamsIds = getAuthorizedExamIds(allEnrollments, teachingDTO);
//        List<Integer> toBeUpdatedLessonsIds = getLessonIdsFromEnrollments(enrollmentsNeedingUpdate);
//        List<Integer> toBeUpdatedExamsIds = getExamIdsFromEnrollments(enrollmentsNeedingUpdate);
//        for (Integer id : toBeUpdatedLessonsIds) {
//            if (!authLessonsIds.contains(id)) {
//                throw new AccessForbiddenException();
//            }
//        }
//        for (Integer id : toBeUpdatedExamsIds) {
//            if (!authExamsIds.contains(id)) {
//                throw new AccessForbiddenException();
//            }
//        }
//    }
}
