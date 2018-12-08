package business_layer;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import bd_config.H2TestConfiguration;
import data_layer.domain.User;
import data_layer.repositories.IUserRepository;

/**
 * Utilitary class for tests.
 * Here you could add methods for inserting data into db, for having presets
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2TestConfiguration.class)
public abstract class BaseIntegrationTest {

    @Autowired
    private IUserRepository userRepository;

    protected User createUser(User u) {
        return userRepository.save(u);
    }

}
