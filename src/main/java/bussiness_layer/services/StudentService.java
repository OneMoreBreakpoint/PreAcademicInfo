package bussiness_layer.services;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;
import bussiness_layer.mappers.EnrollmentMapper;
import data_layer.domain.Enrollment;
import data_layer.domain.Lesson;
import data_layer.domain.PartialExam;
import data_layer.repositories.IEnrollmentRepository;
import utils.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class StudentService implements IStudentService {

    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Override
    public List<EnrollmentDto> getEnrollments(String studUsername) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
        if (enrollments.size() == 0) {
            throw new ResourceNotFoundException();
        }
        List<EnrollmentDto> enrollmentDTOS = EnrollmentMapper.toEntityDtoList(enrollments);
        return enrollmentDTOS;
    }

    @Override
    public SortedSet<LessonDto> getLessonsTemplateSet(String studUsername) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
        List<EnrollmentDto> enrollmentDtos = EnrollmentMapper.toEntityDtoList(enrollments);
        SortedSet<LessonDto> lessonDtosTemplateSet = new TreeSet<>((lessonDto1, lessonDto2) -> {
            if (lessonDto1.equals(lessonDto2)) {
                return 0;
            } else if (lessonDto1.getNr() < lessonDto2.getNr()) {
                return -1;
            } else if (lessonDto1.getNr().equals(lessonDto2.getNr())) {
                if (lessonDto1.getType() == Lesson.LessonType.SEMINAR) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        });
        enrollmentDtos.forEach(enrollmentDto -> enrollmentDto.getLessons().forEach(lessonDto -> lessonDtosTemplateSet.add(lessonDto)));
        return lessonDtosTemplateSet;
    }

    @Override
    public SortedSet<PartialExamDto> getExamsTemplateSet(String studUsername) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(studUsername);
        List<EnrollmentDto> enrollmentDtos = EnrollmentMapper.toEntityDtoList(enrollments);
        SortedSet<PartialExamDto> lessonsTemplateSet = new TreeSet<>((partialExamDto1, partialExamDto2) -> {
            if (partialExamDto1.equals(partialExamDto2)) {
                return 0;
            } else if (partialExamDto1.getNr() < partialExamDto2.getNr()) {
                return -1;
            } else if (partialExamDto1.getNr().equals(partialExamDto2.getNr())) {
                if (partialExamDto1.getType() == PartialExam.PartialExamType.SEMINAR) {
                    return -1;
                } else if (partialExamDto1.getType() == PartialExam.PartialExamType.LABORATORY) {
                    if (partialExamDto2.getType() == PartialExam.PartialExamType.SEMINAR) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        });
        enrollmentDtos.forEach(enrollmentDto -> enrollmentDto.getPartialExams().forEach(partialExamDto -> lessonsTemplateSet.add(partialExamDto)));
        return lessonsTemplateSet;
    }

}
