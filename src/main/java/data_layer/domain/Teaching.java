package data_layer.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Teachings")
@Getter
@Setter
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "TeachingsGroupsSeminar",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> seminarGroups;

    @ManyToMany
    @JoinTable(name = "TeachingsGroupsLaboratory",
            joinColumns = @JoinColumn(name = "teaching_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> laboratoryGroups;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
