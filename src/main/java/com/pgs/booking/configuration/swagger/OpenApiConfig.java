package com.pgs.booking.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

import static com.pgs.booking.configuration.swagger.OpenApiConfig.DESCRIPTION;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "Booking Air Tickets REST Service",
    description = DESCRIPTION,
    version = "0.0.1-SNAPSHOT"))
public class OpenApiConfig {

    static final String DESCRIPTION = """
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

}
