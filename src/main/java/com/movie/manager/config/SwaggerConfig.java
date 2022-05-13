package com.movie.manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApiV1() {
        return GroupedOpenApi.builder()
                .group("public-api-v1")
                .packagesToScan("com.movie.manager")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(final BuildProperties buildProperties, final Environment env) {
        return new OpenAPI()
                .info(new Info()
                        .title(env.getProperty("spring.application.name", "spring-boot-template"))
                        .version(buildProperties.getVersion())
                        .description("Spring boot template description")
                );
    }
}
