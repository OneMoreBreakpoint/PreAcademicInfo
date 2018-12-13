package data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data_layer.domain.Professor;
import data_layer.domain.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    Student findByUsername(String username);
}
