package web_layer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@Slf4j
public class BaseControllerTest {

    protected MockMvc mockMvc;

    @Test
    public void initializerTestMethod() {
        log.info("test in BaseControllerTest");
    }

}
