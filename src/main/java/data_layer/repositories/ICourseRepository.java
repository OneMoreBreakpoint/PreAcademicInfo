package data_layer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data_layer.domain.Course;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);
}
