package data_layer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data_layer.domain.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsername(String username);
}
