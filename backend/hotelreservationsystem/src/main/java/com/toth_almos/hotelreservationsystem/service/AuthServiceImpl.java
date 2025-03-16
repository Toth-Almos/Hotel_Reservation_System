package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.RegisterRequest;
import com.toth_almos.hotelreservationsystem.dto.UserDTO;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.model.User;
import com.toth_almos.hotelreservationsystem.model.UserRole;
import com.toth_almos.hotelreservationsystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO login(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        session.setAttribute("user", user);
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Override
    public UserDTO getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user logged in");
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }

    @Override
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }

        Customer newUser = new Customer();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.CUSTOMER);
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setAddress(request.getAddress());

        userRepository.save(newUser);
        return new UserDTO(newUser.getId(), newUser.getUsername(), newUser.getRole());
    }
}
