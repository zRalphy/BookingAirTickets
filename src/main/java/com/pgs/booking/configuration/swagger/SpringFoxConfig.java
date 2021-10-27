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
        The Booking Air Tickets API is organized around REST. Our API is used for creating flight reservations by users.
        
        ## Authentication
        It uses the Oauth2 for authentication of the performed requests. In order to obtain a Bearer token for \
        performing regular requests user need to first perform a request like this:
        ```
        curl -L -X POST 'http://localhost:8080/oauth/token' \\
        -u {{ClientId}}:{{secret}} \\
        -H 'Accept: application/json' \\
        -H 'Content-Type: application/x-www-form-urlencoded' \\
        --data-urlencode 'username={{user}}' \\
        --data-urlencode 'password={{password}}' \\
        --data-urlencode 'grant_type=password'
        ```
        `ClientId` and `secret` are credentials used for basic authentication. However, `user` and `password` are \
        credentials of the user in database where roles are being assigned.
        If request is successful, we should obtain token from the `access_token` field in response body.
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
