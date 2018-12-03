package data_layer.repositories;

import data_layer.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {
    Course findByCode(String code);
}
