package factory;

import data_layer.domain.Professor;

import static utils.TestConstants.*;

public class ProfessorFactory {
    public static Professor.ProfessorBuilder generateProfessorBuilder() {
        return Professor.builder()
                .username(USERNAME)
                .encryptedPassword(PASSWORD)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL);
        //TODO(All) add rest of the fields when necessary.
    }

    public static Professor generateProfessor() {
        return generateProfessorBuilder().build();
    }

}
