package data_layer.repositories;

import data_layer.domain.Teaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITeachingRepository extends JpaRepository<Teaching, Integer> {
    Teaching findOneByProfessorUsernameAndCourseCode(String profUsername, String courseCode);
}
