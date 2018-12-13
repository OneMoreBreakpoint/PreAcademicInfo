package data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import data_layer.domain.Professor;

public interface IProfessorRepository extends JpaRepository<Professor, Integer> {

    Professor findByUsername(String username);

}
