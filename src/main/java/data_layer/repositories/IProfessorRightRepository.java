package data_layer.repositories;

import data_layer.domain.ProfessorRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utils.LessonType;
import utils.RightType;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProfessorRightRepository extends JpaRepository<ProfessorRight, Integer> {

    @Query("SELECT pr FROM ProfessorRights pr "
            + "WHERE pr.professor.username = (?1) AND pr.course.code = (?2) AND pr.group.code = (?3)")
    List<ProfessorRight> findByProfessorAndCourseAndGroup(String profUsername, String courseCode, String groupCode);

    @Query("SELECT pr FROM ProfessorRights pr "
            + "WHERE pr.professor.username = (?1) AND pr.course.code = (?2)")
    List<ProfessorRight> findByProfessorAndCourse(String profUsername, String courseCode);

    @Query("SELECT pr FROM ProfessorRights pr "
            + "WHERE pr.professor.username = (?1) AND pr.course.code = (?2) AND pr.group.code = (?3) "
            + "AND pr.lessonType = (?4) AND pr.rightType = (?5)")
    Optional<ProfessorRight> findByProfessorAndCourseAndGroupAndLessonTypeAndRightType(String profUsername, String courseCode
            , String groupCode, LessonType lessonType, RightType rightType);

    @Query("SELECT pr FROM ProfessorRights pr "
            + "WHERE pr.professor.username = (?1)")
    List<ProfessorRight> findByProfessor(String profUsername);

}
