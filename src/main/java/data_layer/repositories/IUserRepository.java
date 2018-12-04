package data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data_layer.domain.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findOneByUsername(String username);
}
