package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.LoginRequest;
import com.toth_almos.hotelreservationsystem.dto.RegisterRequest;
import com.toth_almos.hotelreservationsystem.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    public UserDTO login(String username, String password, HttpSession session);
    public void logout(HttpSession session);
    public UserDTO getCurrentUser(HttpSession session);
    public void register(RegisterRequest request);
}
