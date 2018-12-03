package data_layer.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }

}
