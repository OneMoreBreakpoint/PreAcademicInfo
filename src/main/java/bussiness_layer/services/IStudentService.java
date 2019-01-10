package bussiness_layer.services;

import bussiness_layer.dto.StudentDto;

public interface IStudentService {

    void updateStudent(StudentDto studentDto, String studUsername);

}
