package com.melihinci.skeleton.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public GroupedOpenApi authServiceApi() {
        return GroupedOpenApi.builder()
                             .group("auth-service")
                             .pathsToMatch("/auth/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi firstServiceApi() {
        return GroupedOpenApi.builder()
                             .group("first-service")
                             .pathsToMatch("/first/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi secondServiceApi() {
        return GroupedOpenApi.builder()
                             .group("second-service")
                             .pathsToMatch("/second/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi thirdServiceApi() {
        return GroupedOpenApi.builder()
                             .group("third-service")
                             .pathsToMatch("/third/**")
                             .build();
    }

}