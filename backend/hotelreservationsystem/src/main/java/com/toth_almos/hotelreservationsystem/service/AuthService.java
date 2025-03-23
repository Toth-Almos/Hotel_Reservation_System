package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ChangePasswordRequest;
import com.toth_almos.hotelreservationsystem.dto.RegisterRequest;
import com.toth_almos.hotelreservationsystem.dto.UserDTO;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    public UserDTO login(String username, String password, HttpSession session);
    public void logout(HttpSession session);
    public UserDTO getCurrentUser(HttpSession session);
    public UserDTO register(RegisterRequest request);
    public void changePassword(String username, ChangePasswordRequest request);
}
