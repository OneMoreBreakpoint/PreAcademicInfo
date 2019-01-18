package factory;

import bussiness_layer.dto.ProfessorDto;
import data_layer.domain.Professor;
import utils.TestConstants;

import static utils.TestConstants.EMAIL;
import static utils.TestConstants.FIRSTNAME;
import static utils.TestConstants.IMAGE;
import static utils.TestConstants.LASTNAME;
import static utils.TestConstants.PASSWORD;
import static utils.TestConstants.USERNAME;
import static utils.TestConstants.WEB_PAGE;

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

    public static ProfessorDto.ProfessorDtoBuilder generateProfessorDtoBuilder() {
        return ProfessorDto.builder()
                .username(TestConstants.PROF_USERNAME)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .webPage(WEB_PAGE)
                .profilePhoto(IMAGE);
    }

    public static Professor generateProfessor() {
        return generateProfessorBuilder().build();
    }

}
