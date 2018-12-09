package bussiness_layer.services;

import java.util.List;

import bussiness_layer.dto.EnrollmentDto;
import bussiness_layer.dto.TeachingDto;

public interface IStudentService {
    List<EnrollmentDto> getEnrollments(String studUsername);

}
