package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateUserDto;
import com.pgs.booking.model.dto.UserDto;
import com.pgs.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return userService.addUser(createUpdateUserDto);
    }
}
