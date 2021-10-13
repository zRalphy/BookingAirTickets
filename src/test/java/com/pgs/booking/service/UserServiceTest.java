package com.pgs.booking.service;

import com.pgs.booking.model.User;
import com.pgs.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final User USER = User.builder()
            .username("user")
            .build();
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        doReturn(USER)
                .when(userRepository).findByUsername("user");
    }

    @Test
    void testLoadUserByUsername() {
        var user = userService.loadUserByUsername(("user"));
        verify(userRepository).findByUsername("user");
        assertEquals("user", user.getUsername());
    }

    @Test
    void testLoadByUsernameExceptionOccurrence() {
        userService.loadUserByUsername("user");
        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("admin"));
        assertTrue(exception.getMessage().contains("admin"));
    }
}