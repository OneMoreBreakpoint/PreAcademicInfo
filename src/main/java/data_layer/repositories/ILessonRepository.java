package data_layer.repositories;

import data_layer.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Integer> {

    @Modifying
    @Query("DELETE FROM Lessons l WHERE l.template.id = (?1)")
    void deleteByLessonTemplate(Integer lessonTemplateId);
}
