package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.dto.CreateUserDtoMapper;
import com.pgs.booking.mappers.dto.UserDtoMapper;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.dto.UserDto;
import com.pgs.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final UserRepository userRepository;
    private final TokenStore tokenStore;
    private final CreateUserDtoMapper createUserDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public UserDto addUser(CreateUserDto createUserDto){
        var savedUser = userRepository.save(createUserDtoMapper.mapToUser(createUserDto));
        return userDtoMapper.mapToUserDto(savedUser);
    }

    public UserDto activateAndDeactivateUser(Long id, boolean isEnabled) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + id + "not found"));
        user.setEnabled(isEnabled);
        var savedUser = userRepository.save(user);
        return userDtoMapper.mapToUserDto(savedUser);
    }
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        return (UserDetails) Optional.of(token)
                .map(PreAuthenticatedAuthenticationToken::getPrincipal)
                .map(Object::toString)
                .map(tokenStore::readAuthentication)
                .map(OAuth2Authentication::getUserAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new UsernameNotFoundException(token.getName()));
    }
}
