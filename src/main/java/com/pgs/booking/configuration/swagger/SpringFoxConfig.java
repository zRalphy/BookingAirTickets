package com.pgs.booking.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    private static final String DESCRIPTION = """
            The Booking Air Tickets API is organized around REST. 
            Our API is used for creating flight reservations by users.
            It uses the Oauth 2 for authentication of the user.
            """;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Booking Air Tickects REST Service")
                        .description(DESCRIPTION)
                        .build())
                .enable(true)
                .ignoredParameterTypes(PreAuthenticatedAuthenticationToken.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(OpenAPIDefinition.class))
                .build();
    }
}
