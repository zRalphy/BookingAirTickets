package com.pgs.booking.mappers.dto;

import com.pgs.booking.model.Role;
import com.pgs.booking.model.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoMapper {

    public RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
