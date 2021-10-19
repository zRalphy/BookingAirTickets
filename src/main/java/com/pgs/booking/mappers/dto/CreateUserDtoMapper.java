package com.pgs.booking.mappers.dto;

import com.pgs.booking.mappers.entity.RoleEntityMapper;
import com.pgs.booking.model.User;
import com.pgs.booking.model.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserDtoMapper {

    private final RoleEntityMapper roleEntityMapper;

    public User mapToUser(CreateUserDto createUserDto) {
        var roleDtoList = createUserDto.getRoles();
        var roleList =roleDtoList.stream()
                .map(roleEntityMapper::toEntity)
                .toList();
        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .accountNonExpired(createUserDto.isAccountNonExpired())
                .accountNonLocked(createUserDto.isAccountNonLocked())
                .credentialsNonExpired(createUserDto.isCredentialsNonExpired())
                .enabled(createUserDto.isEnabled())
                .roles(roleList)
                .build();
    }
}
