package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.UserDto;
import bussiness_layer.services.IUserService;
import data_layer.domain.Student;
import factory.GroupFactory;
import factory.StudentFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import utils.TestConstants;
import utils.exceptions.AccessForbiddenException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserIT extends BaseIntegrationTest {

    @Autowired
    private IUserService userService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void test() {
        assertEquals(1, 1);
    }

    @Test
    public void givenOneUserSaved_WhenGetUserByUsername_ThenUserIsReturned() {
        //Given
        Student u = StudentFactory.generateStudentBuilder()
                .username("USERNAME2")
                .group(groupRepository.save(GroupFactory.generateGroup()))
                .build();
        createUser(u);

        //When
        UserDto user = userService.getUserByUsername("USERNAME2");

        //Then
        assertEquals("USERNAME2", user.getUsername());
    }

    @Test
    @Transactional
    public void givenCrtPasswordIsOK_WhenPutPassword_ThenPasswordIsUpdated() {
        //Given
        Student u = StudentFactory.generateStudent();
        createUser(u);
        //When
        userService.updatePassword(u.getUsername(), TestConstants.PASSWORD, TestConstants.PASSWORD2);
        //Then
        assertTrue(BCrypt.checkpw(TestConstants.PASSWORD2, u.getEncryptedPassword()));
    }

    @Test
    @Transactional
    public void givenCrtPasswordIsNotOK_WhenPutPassword_ThenAccessForbiddenExceptionIsThrown() {
        //Given
        Student u = StudentFactory.generateStudent();
        createUser(u);
        //Then
        exception.expect(AccessForbiddenException.class);
        //When
        userService.updatePassword(u.getUsername(), TestConstants.PASSWORD2, TestConstants.PASSWORD2);
    }

}
