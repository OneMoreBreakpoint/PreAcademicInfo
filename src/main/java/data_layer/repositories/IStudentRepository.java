package data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data_layer.domain.Professor;
import data_layer.domain.Student;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsername(String username);
}
