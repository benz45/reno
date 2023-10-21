package com.reno.reno.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("local")
public class SwaggerConfiguration {
    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public Docket selectApi() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis((Predicate<RequestHandler>) RequestHandlerSelectors.any())
                .paths((Predicate<String>) PathSelectors.ant("/**"))
                .apis((Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("com.reno.reno.controller"))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("E-Commerce-Store API")
                .description("API reference for developers").version(apiVersion).build();
    }
}
