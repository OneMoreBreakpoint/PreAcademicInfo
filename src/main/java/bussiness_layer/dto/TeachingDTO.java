package bussiness_layer.dto;

import data_layer.domain.Teaching;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private CourseDTO course;

    public TeachingDTO(Teaching entity){
        this.id = entity.getId();
        this.professor = new ProfessorDTO(entity.getProfessor());
        this.seminarGroups = entity.getSeminarGroups().stream().map(GroupDTO::new).collect(Collectors.toSet());
        this.laboratoryGroups = entity.getLaboratoryGroups().stream().map(GroupDTO::new).collect(Collectors.toSet());
        this.course = new CourseDTO(entity.getCourse());
    }

    public SortedSet<GroupDTO> getAllGroups(){
        SortedSet<GroupDTO> allGroups = new TreeSet<>();
        allGroups.addAll(seminarGroups);
        allGroups.addAll(laboratoryGroups);
        return allGroups;
    }
}
