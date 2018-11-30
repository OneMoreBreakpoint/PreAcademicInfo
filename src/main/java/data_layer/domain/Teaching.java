package data_layer.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SortNatural;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Teachings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
