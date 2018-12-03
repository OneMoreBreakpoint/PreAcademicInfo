package data_layer.repositories;

import data_layer.domain.PartialExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IPartialExamRepository extends JpaRepository<PartialExam, Integer> {
    @Query("SELECT exam FROM PartialExams exam " +
            "WHERE exam.enrollment.course.code = (?1) AND exam.enrollment.student.group.code = (?2)")
    Set<PartialExam> findByCourseAndGroup(String courseCode, Short groupCode);
}
