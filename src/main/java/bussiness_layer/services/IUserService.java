package bussiness_layer.services;

import bussiness_layer.dto.UserDto;

public interface IUserService {
    /**
     * Get the correlated user dto for a specific username.
     * @param username
     * @return
     */
    UserDto getUserByUsername(String username);
}
