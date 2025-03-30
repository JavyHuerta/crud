package com.javyhuerta.crud.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Arrays;

@Log4j2
@EnableWebMvc
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Value("${app.cors.pathPattern:/**}")
    private String pathPattern;

    @Value("${app.cors.allowedOrigins:*}")
    private String[] allowedOrigins;

    @Value("${app.cors.allowedHeaders:*}")
    private String[] allowedHeaders;

    @Value("${app.cors.allowedMethods:*}")
    private String[] allowedMethods;

    @Value("${app.cors.maxAge:1800}")
    private long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        log.info("pathPattern: {}", pathPattern);
        log.info("allowedOrigins: {}", Arrays.toString(allowedOrigins));
        log.info("allowedMethods: {}", Arrays.toString(allowedMethods));
        log.info("maxAge: {}", maxAge);

        corsRegistry
                .addMapping(pathPattern)
                .allowedHeaders(allowedHeaders)
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .maxAge(maxAge);
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
