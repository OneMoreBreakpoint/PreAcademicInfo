package data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import data_layer.domain.Professor;

import java.util.Optional;

public interface IProfessorRepository extends JpaRepository<Professor, Integer> {

    Optional<Professor> findByUsername(String username);

}
