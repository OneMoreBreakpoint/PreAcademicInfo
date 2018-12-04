package bussiness_layer.services;

import bussiness_layer.dto.UserDTO;

public interface IUserService {
    UserDTO getUserByUsername(String username);
}
