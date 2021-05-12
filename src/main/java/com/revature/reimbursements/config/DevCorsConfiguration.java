package com.revature.reimbursements.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("development")
public class DevCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/expense-reimbursement/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        registry.addMapping("/user/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        registry.addMapping("/reimbursement/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        registry.addMapping("/auth/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
}
