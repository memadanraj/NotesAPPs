package com.notesAPP.NotesAPP.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            // Allow CORS for Swagger UI and OpenAPI docs
            registry.addMapping("/v3/api-docs").allowedOrigins("http://localhost:8080");
            registry.addMapping("/swagger-ui/**").allowedOrigins("http://localhost:8080");
        }
    }


