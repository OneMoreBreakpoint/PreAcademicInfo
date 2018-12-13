package bussiness_layer.services;

import bussiness_layer.dto.ProfessorDto;
import bussiness_layer.dto.UserDto;

public interface IUserService {
    UserDto getUserByUsername(String username);
    ProfessorDto getProffesorByUsername(String username);
}
