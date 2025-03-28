package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
}
