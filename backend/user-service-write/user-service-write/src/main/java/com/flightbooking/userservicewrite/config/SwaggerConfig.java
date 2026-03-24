package com.flightbooking.userservicewrite.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI userServiceWriteOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service Write API")
                        .description("Handles user registration and login (CQRS Command Side)")
                        .version("1.0.0"));
    }
}