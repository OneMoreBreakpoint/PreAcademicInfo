package data_layer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import data_layer.domain.Professor;
import data_layer.domain.ProfessorCourse;


@Repository
public interface IProfessorCourseRepository extends JpaRepository<ProfessorCourse, Integer> {
    @Query("SELECT pc FROM ProfessorCourses pc WHERE pc.professor.username = (?1) AND pc.course.code = (?2)")
    ProfessorCourse findByProfessorAndCourse(String profUsername, String courseCode);

    List<ProfessorCourse> findByProfessorOrderByCourseAsc(Professor professor);

}
