package data_layer.repositories;

import data_layer.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Integer> {

    @Query("SELECT l FROM Lessons l " +
            "WHERE l.enrollment.course.code = (?1) AND l.enrollment.student.group.code = (?2)")
    Set<Lesson> findByCourseAndGroup(String courseCode, Short groupCode);
}
