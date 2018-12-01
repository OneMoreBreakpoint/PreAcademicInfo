package data_layer.repositories;

import data_layer.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Short> {
    Group findByCode(Short code);
}
