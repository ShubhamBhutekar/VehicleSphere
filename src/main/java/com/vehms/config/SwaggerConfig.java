package com.vehms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TICKET: VEHMS-M01-T008
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI vehmsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VEHMS - Vehicle & Visitor Management System")
                        .description("APIs for managing society residents, their vehicles, and visitor logs.")
                        .version("v0.1 (Module 1)"));
    }
}
