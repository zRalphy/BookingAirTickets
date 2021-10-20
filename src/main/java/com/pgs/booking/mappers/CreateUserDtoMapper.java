package com.pgs.booking.mappers;

import com.pgs.booking.model.entity.User;
import com.pgs.booking.model.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserDtoMapper {



    public User mapToUser(CreateUserDto createUserDto) {
        var roleDtoList = createUserDto.getRoles();

        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .accountNonExpired(createUserDto.isAccountNonExpired())
                .accountNonLocked(createUserDto.isAccountNonLocked())
                .credentialsNonExpired(createUserDto.isCredentialsNonExpired())
                .enabled(createUserDto.isEnabled())
                .roles(roleDtoList)
                .build();
    }
}
