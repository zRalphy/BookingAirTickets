package com.pgs.booking.mappers.entity;

import com.pgs.booking.model.entity.Role;
import com.pgs.booking.model.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleEntityMapper {

    public Role toEntity(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }
}
