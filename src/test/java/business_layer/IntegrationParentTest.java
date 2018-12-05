package business_layer;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bd_config.H2TestConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2TestConfiguration.class)
public abstract class IntegrationParentTest {

}
