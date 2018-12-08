package web_layer.unit;

import bussiness_layer.services.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_layer.BaseControllerTest;
import web_layer.controllers.UserMvcController;

import static junit.framework.TestCase.assertTrue;

public class UserControllerTest extends BaseControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserMvcController userMvcController;

    @Before
    public void init() {
        userMvcController = new UserMvcController(userService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(userMvcController)
                .build();
    }

    @Test
    public void test() {
        assertTrue(true);
    }

}
