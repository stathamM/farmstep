package com.farmstep.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.farmstep.entity.UserEntity;
import com.farmstep.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity register(String name, String email, String password) {

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public UserEntity login(String email, String password) {

        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return null;
        }

        UserEntity user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}