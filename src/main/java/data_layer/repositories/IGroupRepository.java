package data_layer.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import data_layer.domain.Group;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Short> {
    Optional<Group> findByCode(String code);

    @Query("SELECT DISTINCT enrl.student.group FROM Enrollments enrl WHERE enrl.course.code = (?1)")
    Set<Group> findByCourse(String courseCode);

    @Query("SELECT DISTINCT pr.group FROM ProfessorRights pr "
            + "WHERE pr.professor.username = (?1) AND pr.course.code = (?2)")
    List<Group> findByProfessorAndCourse(String profUsername, String courseCode);
}
