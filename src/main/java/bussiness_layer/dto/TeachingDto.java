package bussiness_layer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.SortedSet;

@Data
@NoArgsConstructor
public class TeachingDto {
    private Integer id;
    private ProfessorDto professor;
    private Set<GroupDto> seminarGroups;
    private Set<GroupDto> laboratoryGroups;
    private SortedSet<GroupDto> allGroups;
    private CourseDto course;

}
