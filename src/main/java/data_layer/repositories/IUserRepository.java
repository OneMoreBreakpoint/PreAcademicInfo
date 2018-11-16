package data_layer.repositories;

import data_layer.domain.User;

public interface IUserRepository {
    User findOneByUsername(String username);
}
