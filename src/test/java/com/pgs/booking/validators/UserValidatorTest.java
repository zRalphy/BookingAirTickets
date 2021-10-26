package com.pgs.booking.validators;

import com.pgs.booking.exception.IllegalUserException;
import com.pgs.booking.model.entity.Role;
import com.pgs.booking.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class UserValidatorTest {

    private UserValidator userValidator;

    private static final Role ROLE = Role.builder()
            .name("USER")
            .build();
    private static final List<Role> ROLE_LIST = List.of(ROLE);
    private static final User USER = User.builder()
            .id(1L)
            .roles(ROLE_LIST)
            .build();

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
    }

    @ParameterizedTest
    @CsvSource({
            "2,USER",
            "2,STAFF"
    })
    void throwsException_testValidateSingleUser(Long id, String role) {
        var token = mock(PreAuthenticatedAuthenticationToken.class);
        when(token.getPrincipal()).thenReturn(User.builder()
                .id(id)
                .roles(List.of(Role.builder().name(role).build()))
                .build());
        assertThrows(IllegalUserException.class, () -> userValidator.validateSingleUser(1L, token));
    }

    @ParameterizedTest
    @CsvSource({
            "1,ADMIN",
            "1,USER",
            "2,ADMIN"
    })
    void testValidateSingleUser(Long id, String role) {
        var token = mock(PreAuthenticatedAuthenticationToken.class);
        when(token.getPrincipal()).thenReturn(User.builder()
                .id(id)
                .roles(List.of(Role.builder().name(role).build()))
                .build());
        assertDoesNotThrow(() -> userValidator.validateSingleUser(1L, token));

    }
}