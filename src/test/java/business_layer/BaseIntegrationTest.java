package business_layer;

import org.springframework.beans.factory.annotation.Autowired;

import data_layer.domain.User;
import data_layer.repositories.IUserRepository;

/**
 * Utilitary class for tests.
 * Here you could add methods for inserting data into db, for having presets
 */
public abstract class BaseIntegrationTest extends IntegrationParentTest {

    @Autowired
    private IUserRepository userRepository;

    protected User createUser(User u) {
        return userRepository.save(u);
    }

}
