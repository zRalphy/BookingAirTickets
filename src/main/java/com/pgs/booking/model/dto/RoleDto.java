package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDto {
    private Long id;
    private String name;
}
