package data_layer.repositories;

import data_layer.domain.Teaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITeachingRepository extends JpaRepository<Teaching, Integer> {
    @Query("SELECT t FROM Teachings t WHERE t.professor.username = (?1) AND t.course.code = (?2)")
    Teaching findByProfessorAndCourse(String profUsername, String courseCode);
}
