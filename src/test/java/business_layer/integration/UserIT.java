package business_layer.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.UserDTO;
import bussiness_layer.services.IUserService;
import data_layer.domain.Student;
import factory.StudentFactory;

import static org.junit.Assert.assertEquals;

public class UserIT extends BaseIntegrationTest {

    @Autowired
    private IUserService userService;

    @Test
    public void test() {
        assertEquals(1, 1);
    }

    @Test
    public void givenOneUserSaved_WhenGetAllUsers_ThenUserIsReturned() {
        //Given
        Student u = StudentFactory.generateStudentBuilder()
                .username("USERNAME2")
                .build();
        createUser(u);

        //When
        UserDTO user = userService.getUserByUsername("USERNAME2");

        //Then
        assertEquals(user.getUsername(), "USERNAME2");
    }

}
