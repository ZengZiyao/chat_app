package com.zzy.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String BASE_PACKAGE = "com.zzy.chatapp.controller";
    private static final String TITLE = "chat app";
    private static final String DESCRIPTION = "This is a simple chat application";
    private static final String VERSION = "1.0.0-SNAPSHOT";
    private static final String HEADER = "Authorization";
    private static final String HEADER_TYPE = "string";
    private static final String HEADER_DESCRIPTION = "Bearer Auth Token";
    private static final String PARAMETER_TYPE = "header";
    private static final String HEAD = "Bearer ";
    private static final boolean REQUIRED = false;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION)
                .build();
    }

    private List<Parameter> globalParameterList() {
        return Collections.singletonList(
                new ParameterBuilder()
                        .name(HEADER)
                        .modelRef(new ModelRef(HEADER_TYPE))
                        .required(REQUIRED)
                        .parameterType(PARAMETER_TYPE)
                        .description(HEADER_DESCRIPTION)
                        .defaultValue(HEAD)
                        .build()
        );
    }
}
