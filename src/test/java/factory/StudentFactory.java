package factory;

import data_layer.domain.Student;

import static utils.TestConstants.EMAIL;
import static utils.TestConstants.FATHERS_INITIALS;
import static utils.TestConstants.FIRSTNAME;
import static utils.TestConstants.LASTNAME;
import static utils.TestConstants.PASSWORD;
import static utils.TestConstants.USERNAME;

public class StudentFactory {

    public static Student.StudentBuilder generateStudentBuilder() {
        return Student.builder()
                .username(USERNAME)
                .encryptedPassword(PASSWORD)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .fathersInitials(FATHERS_INITIALS);
        //TODO(All) add rest of the fields when necessary.
    }

    public static Student generateStudent() {
        return generateStudentBuilder().build();
    }

}
