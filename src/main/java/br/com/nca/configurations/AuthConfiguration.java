package br.com.nca.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nca.components.JWTComponent;
import br.com.nca.filters.AuthenticationFilter;

@Configuration
public class AuthConfiguration {    

    @Bean
    FilterRegistrationBean<AuthenticationFilter> authenticationFilter(JWTComponent jwtComponent) {

        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new AuthenticationFilter(jwtComponent));

        registration.addUrlPatterns("/api/*");

        return registration;
    }
}