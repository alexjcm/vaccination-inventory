package com.superapp.firstdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;

import static springfox.documentation.builders.PathSelectors.regex;


/**
 * Swagger V3 Configuration
 *
 * @author alexjcm
 */
//@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api.*"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("API Documentation of Employee Vaccination Inventory")
                .description("List of all endpoints used in REST API")
                .version("v0.1.0")
                .contact(new Contact("Alex John Chamba", "https://alexjcm.me", "alex.chamba@unl.edu.ec"))
                .build();

    }
}


