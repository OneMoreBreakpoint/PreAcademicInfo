package factory;


import data_layer.domain.ProfessorRight;

public class ProfessorRightFactory {
    public static ProfessorRight.ProfessorRightBuilder generateProfessorRightBuilder() {
        return ProfessorRight.builder();
    }

    public static ProfessorRight generateProfessorRight() {
        return generateProfessorRightBuilder().build();
    }
}
