package data_layer.repositories;

import data_layer.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    @Query("SELECT e FROM Enrollments e " +
            "WHERE e.course.code = (?1) AND e.student.group.code = (?2)" +
            "ORDER BY e.student.lastName, e.student.firstName, e.student.fathersInitials")
    List<Enrollment> findByCourseAndGroup(String courseCode, Short groupCode);

    @Query("SELECT e FROM Enrollments e " +
            "WHERE e.course.code = (?1) AND e.student.group.code IN (?2)")
    List<Enrollment> findByCourseAndGroups(String courseCode, Iterable<Short> groupCodes);

}
