package data_layer.repositories;

import data_layer.domain.PartialExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPartialExamRepository extends JpaRepository<PartialExam, Integer> {

}
