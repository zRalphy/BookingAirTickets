package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.dto.UserDto;
import com.pgs.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return userService.addUser(createUserDto);
    }
    @PutMapping("/{id}")
    public UserDto activateOrDeactivateUser(@PathVariable Long id , @RequestParam boolean isEnabled) {
        return userService.activateOrDeactivateUser(id, isEnabled);
    }
}
