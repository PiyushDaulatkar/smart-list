package com.smartlist.core_api.service;

import com.smartlist.core_api.entity.User;
import com.smartlist.core_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // INSIGHT: Dependency Injection
    // 1. Constructor Dependency Injection: Dependencies are passed via constructor.
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
