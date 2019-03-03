package ru.regorov.rrvs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.regorov.rrvs.web.controller.UserController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserController userController;

    //TODO доделать привильные ant паттерны, brypt password encoder
    //TODO Прикрутить basic auth в тестах
    //TODO приделать slf4j к проекту
    //TODO добавить файл с curl примерами запросов
    //TODO сделать exception handlers
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authenticationProvider(authenticationProvider());
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/restaurants/**").hasRole("USER")
//                .antMatchers("/votes/**").hasRole("USER");
    }

//    @Override
//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider());
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userController);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }
}