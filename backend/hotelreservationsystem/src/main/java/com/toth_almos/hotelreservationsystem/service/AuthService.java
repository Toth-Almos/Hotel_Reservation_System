package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ChangePasswordRequest;
import com.toth_almos.hotelreservationsystem.dto.RegisterRequest;
import com.toth_almos.hotelreservationsystem.model.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    public User login(String username, String password, HttpSession session);
    public void logout(HttpSession session);
    public User getCurrentUser(HttpSession session);
    public User register(RegisterRequest request);
    public void changePassword(String username, ChangePasswordRequest request);
    public boolean verifyUser(String token);
}
