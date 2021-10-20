package com.pgs.booking.mappers;


import com.pgs.booking.model.entity.User;
import com.pgs.booking.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {


    public UserDto mapToUserDto(User user) {
        var roleList = user.getRoles();
        var roleStringList = roleList.stream()
                .map(Objects::toString)
                .toList();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .roles(roleStringList)
                .build();
    }
}
