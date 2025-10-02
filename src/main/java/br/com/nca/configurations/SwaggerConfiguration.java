package br.com.nca.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .info(
                new Info()
                    .title("API Agenda de Tarefas")
                    .version("1.0.0")
                    .description("API REST para gerenciamento de tarefas e categorias")
                    .contact(
                        new Contact()
                            .name("Alisson Cruz")
                            .email("adevpr11@gmail.com")
                            .url("https://github.com/a-devrepo"))
                    .license(new License().name("Apache 2.0").url("https://springdoc.org")))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}
