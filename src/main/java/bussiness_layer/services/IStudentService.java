package bussiness_layer.services;

import bussiness_layer.dto.EnrollmentDto;

import java.util.List;

public interface IStudentService {
    List<EnrollmentDto> getEnrollments(String studUsername);
}
