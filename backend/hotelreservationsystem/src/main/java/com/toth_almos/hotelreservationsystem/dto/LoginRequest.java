package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
