package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.LoginRequest;
import com.toth_almos.hotelreservationsystem.dto.RegisterRequest;
import com.toth_almos.hotelreservationsystem.dto.UserDTO;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.model.User;
import com.toth_almos.hotelreservationsystem.model.UserRole;
import com.toth_almos.hotelreservationsystem.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final HttpSession session;

    @Autowired
    public AuthController(AuthService authService, HttpSession session) {
        this.authService = authService;
        this.session = session;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request) {
        UserDTO userDTO = authService.login(request.getUsername(), request.getPassword(), session);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        session.invalidate();
        return ResponseEntity.ok("Logged out!");
    }

    @GetMapping("/current-user")
    public UserDTO getCurrentUser() {
        return authService.getCurrentUser(session);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Successful registration!");
    }
}
