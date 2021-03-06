package factory;

import bussiness_layer.dto.StudentDto;
import data_layer.domain.Student;
import org.mindrot.jbcrypt.BCrypt;
import utils.TestConstants;

import static utils.TestConstants.*;

public class StudentFactory {

    public static Student.StudentBuilder generateStudentBuilder() {
        return Student.builder()
                .username(USERNAME)
                .encryptedPassword(BCrypt.hashpw(PASSWORD, BCrypt.gensalt()))
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .fathersInitials(FATHERS_INITIALS);
        //TODO(All) add rest of the fields when necessary.
    }

    public static Student generateStudent() {
        return generateStudentBuilder().build();
    }

    public static StudentDto.StudentDtoBuilder generateStudentDtoBuilder() {
        return StudentDto.builder()
                .username(TestConstants.STUD_USERNAME)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .group(GroupFactory.generateGroupDto())
                .fathersInitials(FATHERS_INITIALS)
                .profilePhoto(IMAGE);
    }

    public static StudentDto generateStudentDto() {
        return generateStudentDtoBuilder().build();
    }

}
