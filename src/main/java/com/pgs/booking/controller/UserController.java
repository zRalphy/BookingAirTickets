package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUserDto;
import com.pgs.booking.model.dto.UserDto;
import com.pgs.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public UserDto getSingleUser(@Valid @PathVariable Long id) {
        return userService.getSingleUser(id);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return userService.addUser(createUserDto);
    }

    @PutMapping("/{id}/activate")
    public UserDto activateUser(@PathVariable Long id) {
        return userService.activateUser(id);
    }

    @PutMapping("/{id}/deactivate")
    public UserDto deactivateUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }

    @PutMapping("/{id}/setRoles")
    public UserDto setUserRoles(@PathVariable Long id, @RequestBody List<String> roleDtoList) {
        return userService.setUserRoles(id, roleDtoList);
    }
}
