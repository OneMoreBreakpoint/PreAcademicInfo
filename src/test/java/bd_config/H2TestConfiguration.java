package bd_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Configuration
@ComponentScan(basePackages = {"web_layer", "bussiness_layer", "data_layer"})
public class H2TestConfiguration {

    private EntityManagerFactory factory;

    private void initFactory() {
        factory = Persistence.createEntityManagerFactory("test-persistence-unit");
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        if (factory == null) {
            initFactory();
        }
        return factory;
    }

}