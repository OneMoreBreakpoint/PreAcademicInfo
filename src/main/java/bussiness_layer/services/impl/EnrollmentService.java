package bussiness_layer.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import bussiness_layer.utils.ApachePOIExcelWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.mappers.EnrollmentMapper;
import bussiness_layer.services.IEnrollmentService;
import data_layer.domain.Enrollment;
import data_layer.domain.ProfessorRight;
import data_layer.repositories.ICourseRepository;
import data_layer.repositories.IEnrollmentRepository;
import data_layer.repositories.IGroupRepository;
import data_layer.repositories.ILessonRepository;
import data_layer.repositories.IProfessorRightRepository;
import data_layer.repositories.IStudentRepository;
import utils.RightType;
import utils.exceptions.AccessForbiddenException;
import utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class EnrollmentService implements IEnrollmentService {

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IProfessorRightRepository professorRightRepository;

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Override
    public List<EnrollmentDto> getEnrollments(String profUsername, String courseCode, String groupCode) {
        groupRepository.findByCode(groupCode).orElseThrow(ResourceNotFoundException::new);
        courseRepository.findByCode(courseCode).orElseThrow(ResourceNotFoundException::new);
        List<ProfessorRight> rights = professorRightRepository.findByProfessorAndCourseAndGroup(profUsername, courseCode, groupCode);
        if (rights.size() == 0) {
            throw new AccessForbiddenException();
        }
        List<Enrollment> enrollments = enrollmentRepository.findByCourseAndGroup(courseCode, groupCode);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }

        List<EnrollmentDto> enrollmentDTOS = EnrollmentMapper.toDtoList(enrollments);
        stripEnrollmentsOfUnauthorizedLessons(enrollmentDTOS, rights);
        return enrollmentDTOS;
    }

    @Override
    public List<EnrollmentDto> getEnrollments(String studUsername) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }
        return EnrollmentMapper.toDtoList(enrollments);
    }

    public String generateExcelExportFile(String profUsername, String courseCode, String groupCode, boolean publicType){
        List<EnrollmentDto> enrollments = this.getEnrollments(profUsername, courseCode, groupCode);
        ApachePOIExcelWrite apachePOIExcelWrite=new ApachePOIExcelWrite();
        return apachePOIExcelWrite.exportData(enrollments,publicType);
    }

    private void stripEnrollmentsOfUnauthorizedLessons(List<EnrollmentDto> enrollmentDtos, List<ProfessorRight> rights) {
        enrollmentDtos.forEach(enrollmentDto -> {
            List<LessonDto> filteredLessons = enrollmentDto.getLessons().stream()
                    .filter(lessonDto -> {
                        boolean crtProfCanReadLesson = rights.stream()
                                .anyMatch(right -> right.getLessonType() == lessonDto.getTemplate().getType() && right.getRightType() == RightType.READ);
                        boolean crtProfCanWriteLesson = rights.stream()
                                .anyMatch(right -> right.getLessonType() == lessonDto.getTemplate().getType() && right.getRightType() == RightType.WRITE);
                        if (crtProfCanReadLesson) {
                            lessonDto.getTemplate().setRightType(RightType.READ);
                        }
                        if (crtProfCanWriteLesson) {
                            lessonDto.getTemplate().setRightType(RightType.WRITE); //set to higher right
                        }
                        return crtProfCanReadLesson;
                    })
                    .collect(Collectors.toList());
            enrollmentDto.setLessons(filteredLessons);
        });
    }
}
