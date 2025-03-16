package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private UserRole role;
}
