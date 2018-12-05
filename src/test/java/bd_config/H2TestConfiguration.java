package bd_config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"web_layer", "bussiness_layer", "data_layer"})
public class H2TestConfiguration {

}