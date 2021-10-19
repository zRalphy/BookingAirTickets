package com.pgs.booking.mappers.dto;


import com.pgs.booking.model.User;
import com.pgs.booking.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {

    private final RoleDtoMapper roleDtoMapper;
    public UserDto mapToUserDto(User user) {
        var roleList = user.getRoles();
        var roleDtoList = roleList.stream()
                .map(roleDtoMapper::toDto)
                .toList();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .roles(roleDtoList)
                .build();
    }
}
