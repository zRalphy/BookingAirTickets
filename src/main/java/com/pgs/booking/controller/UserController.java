package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.dto.UserDto;
import com.pgs.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return userService.addUser(createUserDto);
    }
    @PutMapping
    public UserDto activateAndDeactivateUser(@PathVariable Long id , @PathVariable boolean isEnabled) {
        return userService.activateAndDeactivateUser(id, isEnabled);
    }
}
