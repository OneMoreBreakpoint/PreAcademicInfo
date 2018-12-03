package bussiness_layer.dto;

import data_layer.domain.Group;
import data_layer.domain.Teaching;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TeachingDTO {
    private Integer id;
    private ProfessorDTO professor;
    private Set<GroupDTO> seminarGroups;
    private Set<GroupDTO> laboratoryGroups;
    private SortedSet<GroupDTO> allGroups;
    private CourseDTO course;

    public TeachingDTO(Teaching entity) {
        this.id = entity.getId();
        this.professor = new ProfessorDTO(entity.getProfessor());
        this.seminarGroups = entity.getSeminarGroups().stream().map(GroupDTO::new).collect(Collectors.toSet());
        this.laboratoryGroups = entity.getLaboratoryGroups().stream().map(GroupDTO::new).collect(Collectors.toSet());
        this.allGroups = new TreeSet<>();
        this.allGroups.addAll(seminarGroups);
        this.allGroups.addAll(laboratoryGroups);
        this.course = new CourseDTO(entity.getCourse());
    }


}
