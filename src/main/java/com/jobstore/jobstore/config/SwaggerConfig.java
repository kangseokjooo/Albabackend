package com.jobstore.jobstore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition (
        info = @Info(title = "Alba Store API",
                description = "Alba Store 서비스 API 명세서",
                version = "v1"),
        servers = {
            @Server(url = "/", description = "Server url")
        }
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        //        SecurityScheme securityScheme = new SecurityScheme()
        //                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
        //                .in(SecurityScheme.In.HEADER).name("Authorization");
        //        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI();
                //.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                //.security(Arrays.asList(securityRequirement));
    }
}
