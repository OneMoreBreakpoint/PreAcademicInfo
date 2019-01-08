package data_layer.repositories;

import data_layer.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);
}
