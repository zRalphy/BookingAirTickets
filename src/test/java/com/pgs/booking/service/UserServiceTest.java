package com.pgs.booking.service;

import com.pgs.booking.mappers.CreateUserDtoMapper;
import com.pgs.booking.mappers.dto.RoleDtoMapper;
import com.pgs.booking.mappers.UserDtoMapper;
import com.pgs.booking.mappers.entity.RoleEntityMapper;
import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.dto.RoleDto;
import com.pgs.booking.model.entity.Role;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.repository.RoleRepository;
import com.pgs.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    private static final Role ROLE_1 = Role.builder()
            .name("USER")
            .build();
    private static final Role ROLE_2 = Role.builder()
            .name("ADMIN")
            .build();
    private static final String ROLE_3 = "USER";
    private static final String ROLE_4 = "ADMIN";
    private static final List<Role> ROLE_LIST = List.of(ROLE_1);
    private static final User USER_1 = User.builder()
            .id(2L)
            .username("user1")
            .roles(ROLE_LIST)
            .build();
    private static final User USER_2 = User.builder()
            .id(3L)
            .username("user2")
            .enabled(true)
            .roles(ROLE_LIST)
            .build();
    private static final CreateUserDto CREATE_USER_DTO = CreateUserDto.builder()
            .username("user")
            .roles(List.of(ROLE_3, ROLE_4))
            .build();
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    @Spy
    private UserService userService;

    @Spy
    private TokenStore tokenStore = new InMemoryTokenStore();
    private CreateUserDtoMapper createUserDtoMapper = new CreateUserDtoMapper();
    private UserDtoMapper userDtoMapper = new UserDtoMapper();

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetSingleUser() {
        var optionalUser = Optional.of(USER_1);
        when(userRepository.findById(2L)).thenReturn(optionalUser);
        var userDto = userService.getSingleUser(2L);
        verify(userRepository).findById(2L);
        assertEquals(userDto.getUsername(), USER_1.getUsername());
        assertEquals(userDto.isEnabled(), USER_1.isEnabled());
        assertEquals(userDto.isAccountNonExpired(), USER_1.isAccountNonExpired());
        assertEquals(userDto.isCredentialsNonExpired(), USER_1.isCredentialsNonExpired());
        assertEquals(userDto.isAccountNonLocked(), USER_1.isAccountNonLocked());
    }

    @Test
    void testAddUser() {
        when(userRepository.save(any(User.class)))
                .thenReturn(USER_1);
        var userDto = userService.addUser(CREATE_USER_DTO);
        verify(userRepository).save(any(User.class));
        assertEquals(userDto.getId(), USER_1.getId());
        assertEquals(userDto.getUsername(), USER_1.getUsername());
        assertEquals(userDto.getRoles().get(0).getId(), USER_1.getRoles().get(0).getId());
        assertEquals(userDto.getRoles().get(0).getName(), USER_1.getRoles().get(0).getName());
    }
    @Test
    void testActivateUser() {
        var optionalUser = Optional.of(USER_2);
        when(userRepository.findById(3L)).thenReturn(optionalUser);
        when(userRepository.save(any(User.class))).thenReturn(USER_2);
        var userDto = userService.activateUser(3L);
        verify(userRepository).findById(3L);
        verify(userRepository).save(any(User.class));
        assertEquals(userDto.isEnabled(), USER_2.isEnabled());
    }

    @Test
    void testDeactivateUser() {
        var optionalUser = Optional.of(USER_1);
        when(userRepository.findById(2L)).thenReturn(optionalUser);
        when(userRepository.save(any(User.class))).thenReturn(USER_1);
        var userDto = userService.activateUser(2L);
        verify(userRepository).findById(2L);
        verify(userRepository).save(any(User.class));
        assertEquals(userDto.isEnabled(), USER_1.isEnabled());
    }

    @Test
    void testSetUserRoles() {
        var optionalUser = Optional.of(USER_1);
        when(userRepository.findById(2L))
                .thenReturn(optionalUser);
        when(roleRepository.findByName(any(String.class)))
                .thenReturn(ROLE_2);
        when(userRepository.save(any(User.class)))
                .thenReturn(USER_1);
        List<String> userRoleList = List.of("ADMIN");
        var userDto = userService.setUserRoles(2L, userRoleList);
        verify(roleRepository).findByName(any(String.class));
        verify(userRepository).save(any(User.class));
        System.out.println(userDto.getRoles().get(0).getName() + USER_1.getRoles().get(0).getName());
        assertEquals(userDto.getRoles().get(0).getName(), USER_1.getRoles().get(0).getName());
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUsername("user1"))
                .thenReturn(USER_1);
        var user = userService.loadUserByUsername(("user1"));
        verify(userRepository).findByUsername("user1");
        assertEquals(user.getUsername(), USER_1.getUsername());
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