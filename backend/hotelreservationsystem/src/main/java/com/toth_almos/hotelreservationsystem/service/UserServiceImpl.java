package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.User;
import com.toth_almos.hotelreservationsystem.model.UserRole;
import com.toth_almos.hotelreservationsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
