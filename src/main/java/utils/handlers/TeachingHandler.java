package utils.handlers;

import bussiness_layer.dto.CourseDTO;
import bussiness_layer.dto.GroupDTO;
import bussiness_layer.dto.ProfessorDTO;
import bussiness_layer.dto.TeachingDTO;
import data_layer.domain.Group;
import data_layer.domain.Professor;
import data_layer.domain.Teaching;

public class TeachingHandler {

    public static boolean professorHasSeminarRightsOverGroup(TeachingDTO teaching, Short groupCode){
        return teaching.getSeminarGroups().contains(new GroupDTO(groupCode));
    }

    public static boolean professorHasSeminarRightsOverGroup(Teaching teaching, Short groupCode){
        return teaching.getSeminarGroups().contains(new Group(groupCode));
    }

    public static boolean professorHasLaboratoryRightsOverGroup(TeachingDTO teaching, Short groupCode){
        return teaching.getLaboratoryGroups().contains(new GroupDTO(groupCode));
    }

    public static boolean professorHasLaboratoryRightsOverGroup(Teaching teaching, Short groupCode){
        return teaching.getLaboratoryGroups().contains(new Group(groupCode));
    }

    public static boolean professorHasCoordinatorRights(TeachingDTO teaching){
        return teaching.getProfessor().equals(teaching.getCourse().getCoordinator());
    }

    public static boolean professorHasCoordinatorRights(Teaching teaching){
        return teaching.getProfessor().equals(teaching.getCourse().getCoordinator());
    }

}
