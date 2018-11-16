package web_layer.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] staticResources  =  {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/js/**",
                "/dependencies/**"
        };
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/student/*").hasRole("STUDENT")
                .antMatchers("/professor/*").hasRole("PROFESSOR")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?error=true")
                .and()
                .logout().logoutSuccessUrl("/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
