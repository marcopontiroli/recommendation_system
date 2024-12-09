package com.conentwise.recommendationsystem.recommendationsystem.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

import com.conentwise.recommendationsystem.recommendationsystem.web.validators.InteractionInsertDTOValidator;

@Configuration
public class ValidatorConfig {

    @Bean
    Validator validator() {
        return new InteractionInsertDTOValidator();
    }
}