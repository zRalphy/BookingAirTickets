package com.pgs.booking.service;

import com.pgs.booking.mappers.dto.CreateUserDtoMapper;
import com.pgs.booking.mappers.dto.RoleDtoMapper;
import com.pgs.booking.mappers.dto.UserDtoMapper;
import com.pgs.booking.mappers.entity.RoleEntityMapper;
import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.dto.RoleDto;
import com.pgs.booking.model.entity.Role;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Role ROLE = Role.builder()
            .id(3L)
            .name("USER")
            .build();
    private static final RoleDto ROLE_DTO = RoleDto.builder()
            .id(3L)
            .name("USER")
            .build();
    private static final List<Role> ROLE_LIST = List.of(ROLE);
    private static final List<RoleDto> ROLE_DTO_LIST = List.of(ROLE_DTO);
    private static final User USER_1 = User.builder()
            .id(2L)
            .username("user")
            .roles(ROLE_LIST)
            .build();
    private static final CreateUserDto CREATE_USER_DTO = CreateUserDto.builder()
            .username("user")
            .roles(ROLE_DTO_LIST)
            .build();
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    private RoleEntityMapper roleEntityMapper = new RoleEntityMapper();
    private RoleDtoMapper roleDtoMapper = new RoleDtoMapper();
    @Spy
    private TokenStore tokenStore = new InMemoryTokenStore();
    private CreateUserDtoMapper createUserDtoMapper = new CreateUserDtoMapper(roleEntityMapper);
    private UserDtoMapper userDtoMapper = new UserDtoMapper(roleDtoMapper);

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, tokenStore, createUserDtoMapper, userDtoMapper);
    }

    @Test
    void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(USER_1);
        var userDto = userService.addUser(CREATE_USER_DTO);
        verify(userRepository).save(any(User.class));
        assertEquals(userDto.getId(), USER_1.getId());
        assertEquals(userDto.getUsername(), USER_1.getUsername());
        assertEquals(userDto.getRoles().get(0).getId(), USER_1.getRoles().get(0).getId());
        assertEquals(userDto.getRoles().get(0).getName(), USER_1.getRoles().get(0).getName());
    }

    @Test
    void testActivateAndDeactivateUser() {
        var optionalUser = Optional.of(USER_1);
        when(userRepository.findById(2L)).thenReturn(optionalUser);
        var user = User.builder()
                .id(2L)
                .username("user")
                .enabled(true)
                .roles(ROLE_LIST)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        var userDto = userService.activateAndDeactivateUser(2L, true);
        verify(userRepository).findById(2L);
        verify(userRepository).save(any(User.class));
        assertEquals(userDto.isEnabled(), user.isEnabled());
    }

    @Test
    void testLoadUserByUsername() {
        doReturn(USER_1)
                .when(userRepository).findByUsername("user");
        var user = userService.loadUserByUsername(("user"));
        verify(userRepository).findByUsername("user");
        assertEquals("user", user.getUsername());
    }

    @Test
    void testLoadByUsernameExceptionOccurrence() {
        when(userRepository.findByUsername("user"))
                .thenReturn(USER_1);
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
            when(token.getPrincipal())
                    .thenReturn(tokenString);
            when(tokenStore.readAuthentication(tokenString))
                    .thenReturn(oAuth2Authentication);
            when(oAuth2Authentication.getUserAuthentication())
                    .thenReturn(authentication);
        }

        @Test
        void testLoadUserDetails() {
            when(authentication.isAuthenticated())
                    .thenReturn(true);
            when(authentication.getPrincipal())
                    .thenReturn(userDetails);
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