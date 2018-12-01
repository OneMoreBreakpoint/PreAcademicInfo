package data_layer.repositories;

import data_layer.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfessorRepository extends JpaRepository<Professor, Integer> {
    Professor findByUsername(String username);
}
