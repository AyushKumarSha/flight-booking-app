package com.flightbooking.userserviceread.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI userServiceReadOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service Read API")
                        .description("Handles user queries (CQRS Query Side)")
                        .version("1.0.0"));
    }
}