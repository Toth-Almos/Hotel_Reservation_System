package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.ChangePasswordRequest;
import com.toth_almos.hotelreservationsystem.dto.LoginRequest;
import com.toth_almos.hotelreservationsystem.dto.RegisterRequest;
import com.toth_almos.hotelreservationsystem.dto.UserDTO;
import com.toth_almos.hotelreservationsystem.mapper.UserMapper;
import com.toth_almos.hotelreservationsystem.model.User;
import com.toth_almos.hotelreservationsystem.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    private final AuthService authService;
    private final HttpSession session;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(AuthService authService, HttpSession session, UserMapper userMapper) {
        this.authService = authService;
        this.session = session;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getUsername(), request.getPassword(), session);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout(session);
        return ResponseEntity.ok("Logged out!");
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = authService.getCurrentUser(session);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestBody ChangePasswordRequest request) {
        authService.changePassword(username, request);
        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        boolean verified = authService.verifyUser(token);
        System.out.println("Verification endpoint reached!");
        if (verified) {
            return ResponseEntity.ok("Email successfully verified. You may now log in.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }
    }
}
