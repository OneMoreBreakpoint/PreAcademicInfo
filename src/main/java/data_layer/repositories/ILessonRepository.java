package data_layer.repositories;

import data_layer.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Integer> {
}
