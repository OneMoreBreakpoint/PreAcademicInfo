package data_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data_layer.domain.Professor;

@Repository
public interface IProffesorRepository extends JpaRepository<Professor, Integer> {
    Professor findByUsername(String username);
}
