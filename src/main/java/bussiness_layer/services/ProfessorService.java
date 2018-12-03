package bussiness_layer.services;

import bussiness_layer.dto.*;
import bussiness_layer.utils.Authorizer;
import data_layer.domain.*;
import data_layer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.exceptions.AccessForbiddenException;
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
    private IEnrollmentRepository enrollmentRepository;

    @Autowired
    private ITeachingRepository teachingRepository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private IPartialExamRepository partialExamRepository;

    @Autowired
    private ILessonRepository lessonRepository;

    @Autowired
    private ICourseRepository courseRepository;


    @Override
    public List<EnrollmentDTO> getEnrollmentsForProfessorByCourseAndGroup(String profUsername, String courseCode, Short groupCode) {
        if (groupRepository.findByCode(groupCode) == null || courseRepository.findByCode(courseCode) == null) {
            throw new ResourceNotFoundException();
        }
        Teaching teaching = teachingRepository.findByProfessorAndCourse(profUsername, courseCode);
        if (teaching == null) {
            throw new AccessForbiddenException();
        }
        TeachingDTO teachingDTO = new TeachingDTO(teaching);
        if (!Authorizer.teachingHasSemRightsOverGroup(teachingDTO, groupCode)
                && !Authorizer.teachingHasLabRightsOverGroup(teachingDTO, groupCode)
                && !Authorizer.teachingHasCoordinatorRights(teachingDTO)) {
            throw new AccessForbiddenException();
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
        if (Authorizer.teachingHasCoordinatorRights(teachingDTO)) { //coordinator can view all groups
            addAllGroupsToTeachingForCourse(teachingDTO, courseCode);
        }
        return teachingDTO;
    }

    @Override
    public void updateEnrollments(String profUsername, List<EnrollmentDTO> enrollmentsFromClient) {
        if (enrollmentsFromClient.size() == 0) {
            return;
        }
        List<Enrollment> enrollmentEntities = enrollmentsFromClient.stream()
                .map(EnrollmentDTO::toEntity).collect(Collectors.toList());
        List<Lesson> lessonsFromClient = enrollmentEntities.stream().map(Enrollment::getLessons)
                .flatMap(Collection::stream).collect(Collectors.toList());
        List<PartialExam> examsFromClient = enrollmentEntities.stream().map(Enrollment::getPartialExams)
                .flatMap(Collection::stream).collect(Collectors.toList());
        String courseCode = enrollmentsFromClient.get(0).getCourse().getCode();
        Short groupCode = enrollmentsFromClient.get(0).getStudent().getGroup().getCode();
        authorizeUpdateLessonsAndExams(lessonsFromClient, examsFromClient, profUsername, courseCode, groupCode);
        lessonRepository.saveAll(lessonsFromClient);
        partialExamRepository.saveAll(examsFromClient);
        lessonRepository.flush();
        partialExamRepository.flush();
    }

    public void stripUnauthorizedReadLessonsAndExams(List<EnrollmentDTO> enrollments, TeachingDTO teaching, Short groupCode) {
        enrollments.forEach(enrollment -> {
            //keep only readable lessons and exams
            List<LessonDTO> authLessons = enrollment.getLessons().stream()
                    .filter(lesson -> Authorizer.teachingCanReadLesson(teaching, groupCode, lesson))
                    .collect(Collectors.toList());
            List<PartialExamDTO> authExams = enrollment.getPartialExams().stream()
                    .filter(exam -> Authorizer.teachingCanReadExam(teaching, groupCode, exam))
                    .collect(Collectors.toList());
            authLessons.forEach(lessonDTO -> lessonDTO.setReadonly(Authorizer.teachingCanOnlyReadLesson(teaching, groupCode, lessonDTO)));
            authExams.forEach(examDTO -> examDTO.setReadonly(Authorizer.teachingCanOnlyReadExam(teaching,groupCode, examDTO)));
            enrollment.setLessons(authLessons);
            enrollment.setPartialExams(authExams);
        });
    }
    
    private void authorizeUpdateLessonsAndExams(List<Lesson> lessonsFromClient, List<PartialExam> examsFromClient
            , String profUsername, String courseCode, Short groupCode){
        //get readable lessons and exams
        List<EnrollmentDTO> enrollmentsFromDB = getEnrollmentsForProfessorByCourseAndGroup(profUsername, courseCode, groupCode);
        TeachingDTO teachingDTO = getTeachingByProfessorAndCourse(profUsername, courseCode);
        //strip readonly lessons and exams and get their ids
        Set<Integer> writableLessonsIds = enrollmentsFromDB.stream()
                .map(EnrollmentDTO::getLessons).flatMap(Collection::stream)
                .filter(lesson -> Authorizer.teachingCanWriteLesson(teachingDTO, groupCode, lesson))
                .map(LessonDTO::getId).collect(Collectors.toSet());
        Set<Integer> writableExamsIds = enrollmentsFromDB.stream()
                .map(EnrollmentDTO::getPartialExams).flatMap(Collection::stream)
                .filter(exam -> Authorizer.teachingCanWriteExam(teachingDTO, groupCode, exam))
                .map(PartialExamDTO::getId).collect(Collectors.toSet());
        //get ids from client
        List<Integer> lessonsFromClientIds = lessonsFromClient.stream().map(Lesson::getId).collect(Collectors.toList());
        List<Integer> examsFromClientIds = examsFromClient.stream().map(PartialExam::getId).collect(Collectors.toList());
        //there shouldn't be any ids from client that don't exist in ids from db
        lessonsFromClientIds.forEach(id -> {
            if (!writableLessonsIds.contains(id)) throw new AccessForbiddenException();
        });
        examsFromClientIds.forEach(id->{
            if (!writableExamsIds.contains(id)) throw new AccessForbiddenException();
        });
    }
    

    private void addAllGroupsToTeachingForCourse(TeachingDTO teachingDTO, String courseCode) {
        Set<GroupDTO> allGroupsTakingThisCourse = groupRepository.findByCourse(courseCode)
                .stream().map(GroupDTO::new).collect(Collectors.toSet());
        teachingDTO.getAllGroups().addAll(allGroupsTakingThisCourse);
    }

}
