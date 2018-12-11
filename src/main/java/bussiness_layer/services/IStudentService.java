package bussiness_layer.services;

import java.util.List;
import java.util.SortedSet;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.LessonDto;
import bussiness_layer.dto.PartialExamDto;

public interface IStudentService {
    List<EnrollmentDto> getEnrollments(String studUsername);

    SortedSet<LessonDto> getLessonsTemplateSet(String studUsername);

    SortedSet<PartialExamDto> getExamsTemplateSet(String studUsername);
}
