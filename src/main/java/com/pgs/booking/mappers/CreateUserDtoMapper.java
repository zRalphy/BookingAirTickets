package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserDtoMapper {

    public User mapToUser(CreateUserDto createUserDto) {
        return User.builder()
                .username(createUserDto.getUsername())
                .accountNonExpired(createUserDto.isAccountNonExpired())
                .accountNonLocked(createUserDto.isAccountNonLocked())
                .credentialsNonExpired(createUserDto.isCredentialsNonExpired())
                .enabled(createUserDto.isEnabled())
                .build();
    }
}
