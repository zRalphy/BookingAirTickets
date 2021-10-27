package com.pgs.booking.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    private static final String DESCRIPTION = """
        The Booking Air Tickets API is organized around REST. 
        Our API is used for creating flight reservations by users.
        It uses the Oauth 2 for authentication of the user.
        When user successfully login to our API, 
        he automatically send request for token and assign this token to him.
        """;

    private static final List<Response> GENERIC_RESPONSES = List.of(
        new ResponseBuilder()
            .code("400")
            .description("Invalid request")
            .build(),
        new ResponseBuilder()
            .code("401")
            .description("Request is not authenticated")
            .build(),
        new ResponseBuilder()
            .code("403")
            .description("User is not authorised for this action")
            .build(),
        new ResponseBuilder()
            .code("500")
            .description("Internal Server Error")
            .build()
        );

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
            .build()
            .useDefaultResponseMessages(true)
            .globalRequestParameters(List.of(
                new RequestParameterBuilder()
                    .in(ParameterType.HEADER)
                    .name(HttpHeaders.AUTHORIZATION)
                    .description("Bearer token")
                    .required(true)
                    .build()))
            .globalResponses(HttpMethod.GET, GENERIC_RESPONSES)
            .globalResponses(HttpMethod.PUT, GENERIC_RESPONSES)
            .globalResponses(HttpMethod.POST, GENERIC_RESPONSES)
            .globalResponses(HttpMethod.DELETE, GENERIC_RESPONSES);
    }
}
