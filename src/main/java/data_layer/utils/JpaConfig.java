package data_layer.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

    @Bean(name="transactionManager")
    public PlatformTransactionManager dbTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                dbEntityManager().getObject());
        return transactionManager;
    }

}
