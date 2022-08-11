package com.hypnotoad.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:datasourceSecrets.properties")
public class SpringConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public String hackathonUrl(@Value("${url.url}") String url) {
        return url;
    }

    @Bean
    public String staticPath(@Value("${url.static.path}") String staticPath) {
        return staticPath;
    }

    @Bean
    public String staticUrl(String hackathonUrl, String staticPath) {
        return hackathonUrl + staticPath;
    }
}
