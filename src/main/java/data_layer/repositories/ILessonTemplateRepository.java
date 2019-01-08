package data_layer.repositories;

import data_layer.domain.LessonTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILessonTemplateRepository extends JpaRepository<LessonTemplate, Integer> {

    @Query("SELECT course.lessonTemplates FROM Courses course WHERE course.code = (?1)")
    List<LessonTemplate> findByCourse(String courseCode);
}
