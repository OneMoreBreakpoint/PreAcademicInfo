package bussiness_layer.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import data_layer.domain.Course;
import data_layer.domain.Group;
import data_layer.domain.Professor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRightsDto {

    private Set<Group> seminarGroups;

    private Set<Group> laboratoryGroups;

    @NotNull
    private Professor professor;

    @NotNull
    private Course course;

}
