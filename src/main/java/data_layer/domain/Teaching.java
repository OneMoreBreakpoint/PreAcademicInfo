package data_layer.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "Teachings")
@Getter
@Setter
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Professor professor;

    @ManyToMany
    @JoinTable(name = "TeachingsGroupsSeminar",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> seminarGroups;

    @ManyToMany
    @JoinTable(name = "TeachingsGroupsLaboratory",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> laboratoryGroups;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
