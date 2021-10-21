package com.pgs.booking.mappers;


import com.pgs.booking.model.dto.UserDto;
import com.pgs.booking.model.entity.Role;
import com.pgs.booking.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoMapper {


    public UserDto mapToUserDto(User user) {
        var roleList = user.getRoles();
        var roleStringList = roleList.stream()
                .map(Role::getName)
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
