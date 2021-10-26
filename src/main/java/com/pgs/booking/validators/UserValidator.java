package com.pgs.booking.validators;

import com.pgs.booking.model.entity.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserValidator {

    public void handleSingleUser(Long id, PreAuthenticatedAuthenticationToken token) {
        if(token.getPrincipal() instanceof User user) {
            if(!Objects.equals(user.getId(), id) || !user.getRoles().contains("ADMIN")){
                throw new IllegalArgumentException("The token doesn't have the permission to access single user.");
            }
        }
    }
}
