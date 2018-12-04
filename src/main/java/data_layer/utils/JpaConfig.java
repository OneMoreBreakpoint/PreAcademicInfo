package data_layer.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("data_layer.repositories")
public class JpaConfig {

    private EntityManagerFactory factory;

    private void initFactory() {
        factory = Persistence.createEntityManagerFactory("main-persistence-unit");
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        if (factory == null) {
            initFactory();
        }
        return factory;
    }

}
