package com.shortly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shortly.generator.ShortCodeGenerator;
import com.shortly.generator.RandomStringShortCodeGenerator;

@Configuration
public class ShortCodeGeneratorConfig {

    @Bean
    public ShortCodeGenerator shortCodeGenerator() {
        return new RandomStringShortCodeGenerator();
    }
}