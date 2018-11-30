package bussiness_layer.dto;

import data_layer.domain.Teaching;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TeachingDTO {
    private Integer id;
    private ProfessorDTO professor;
    private SortedSet<GroupDTO> groups;
    private CourseDTO course;

    public TeachingDTO(Teaching entity){
        this.id = entity.getId();
        this.professor = new ProfessorDTO(entity.getProfessor());
        this.groups = new TreeSet<>();
        this.groups.addAll(entity.getSeminarGroups().stream().map(GroupDTO::new).collect(Collectors.toList()));
        this.groups.addAll(entity.getLaboratoryGroups().stream().map(GroupDTO::new).collect(Collectors.toList()));
        this.course = new CourseDTO(entity.getCourse());
    }
}
