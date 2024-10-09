package com.valerio.demo_park_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .components(new Components().addSecuritySchemes("security", securityScheme()))
            .info(new Info()
                    .title("REST API - SPRINGPARK")
                    .description("API para fazer gestão de um estacionamento de parque")
                    .version("v1")
                    .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
            );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
            .description("Insira um Bearer Token válido pra continuar")
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("security");
    }
}
