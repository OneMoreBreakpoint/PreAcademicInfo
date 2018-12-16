package data_layer.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that encapsulates a professor entity and a two lists of groups for seminar and laboratory.
 */
@Entity(name = "ProfessorCourses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Professor professor;

    @ManyToMany
    @JoinTable(name = "ProfessorSeminarGroups",
            joinColumns = @JoinColumn(name = "professor_course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> seminarGroups;

    @ManyToMany
    @JoinTable(name = "ProfessorLaboratoryGroups",
            joinColumns = @JoinColumn(name = "professor_course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> laboratoryGroups;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
