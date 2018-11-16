package data_layer.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class EntityManagerUtil {

    private EntityManagerFactory factory;

    private void initFactory() {
        factory = Persistence.createEntityManagerFactory("main-persistence-unit");
    }

    @Bean
    public EntityManager getEntityManager() {
        if (factory == null) {
            initFactory();
        }
        return factory.createEntityManager();
    }

}
