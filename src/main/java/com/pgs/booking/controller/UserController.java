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
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserDto getSingleUser(@Valid @PathVariable Long id) {
        return userService.getSingleUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserDto addUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return userService.addUser(createUserDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/activate")
    public UserDto activateUser(@PathVariable Long id) {
        return userService.activateUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/deactivate")
    public UserDto deactivateUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/setRoles")
    public UserDto setUserRoles(@PathVariable Long id, @RequestBody List<String> roleDtoList) {
        return userService.setUserRoles(id, roleDtoList);
    }
}
