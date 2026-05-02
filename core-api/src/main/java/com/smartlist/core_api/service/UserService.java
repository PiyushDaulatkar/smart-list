package com.smartlist.core_api.service;

import com.smartlist.core_api.dto.user.CreateUserRequest;
import com.smartlist.core_api.dto.user.UserResponse;
import com.smartlist.core_api.entity.User;
import com.smartlist.core_api.mapper.UserMapper;
import com.smartlist.core_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // INSIGHT: Dependency Injection
    // 1. Constructor Dependency Injection: Dependencies are passed via constructor.
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
