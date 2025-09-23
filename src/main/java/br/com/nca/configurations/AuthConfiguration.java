package br.com.nca.configurations;

import br.com.nca.components.JWTComponent;
import br.com.nca.filters.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
