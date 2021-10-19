package com.pgs.booking.service;

import com.pgs.booking.model.User;
import com.pgs.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final User USER = User.builder()
            .username("user")
            .build();
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenStore tokenStore;
    @InjectMocks
    private UserService userService;

    @Test
    void testLoadUserByUsername() {
        doReturn(USER)
                .when(userRepository).findByUsername("user");
        var user = userService.loadUserByUsername(("user"));
        verify(userRepository).findByUsername("user");
        assertEquals("user", user.getUsername());
    }

    @Test
    void testLoadByUsernameExceptionOccurrence() {
        doReturn(USER)
            .when(userRepository).findByUsername("user");
        userService.loadUserByUsername("user");
        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("admin"));
        assertTrue(exception.getMessage().contains("admin"));
    }

    @Nested
    class LoadUserDetailsTest {
        @Mock
        private Authentication authentication;
        @Mock
        private UserDetails userDetails;
        @Mock
        private PreAuthenticatedAuthenticationToken token;

        @BeforeEach
        void setUp() {
            String tokenString = "QWERTY";
            var oAuth2Authentication = mock(OAuth2Authentication.class);

            when(token.getPrincipal()).thenReturn(tokenString);
            when(tokenStore.readAuthentication(tokenString)).thenReturn(oAuth2Authentication);
            when(oAuth2Authentication.getUserAuthentication()).thenReturn(authentication);
        }

        @Test
        void testLoadUserDetails() {
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(userDetails);

            var userDetailsService = userService.loadUserDetails(token);
            assertEquals(userDetails, userDetailsService);

        }

        @Test
        void testLoadUserDetails_Unauthenticated() {
            when(authentication.isAuthenticated()).thenReturn(false);
            assertThrows(UsernameNotFoundException.class, () -> userService.loadUserDetails(token));
        }
    }
}