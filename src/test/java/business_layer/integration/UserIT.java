package business_layer.integration;

import business_layer.BaseIntegrationTest;
import bussiness_layer.dto.UserDto;
import bussiness_layer.services.IUserService;
import data_layer.domain.Student;
import factory.GroupFactory;
import factory.StudentFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class UserIT extends BaseIntegrationTest {

    @Autowired
    private IUserService userService;

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

}
