package com.smartlist.core_api.controller;

import com.smartlist.core_api.dto.user.CreateUserRequest;
import com.smartlist.core_api.dto.user.UserResponse;
import com.smartlist.core_api.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest user) {
        return userService.createUser(user);
    }
}
