package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.Customer;
import lombok.Data;

@Data
public class CustomerDTO {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;

    public CustomerDTO(Customer customer) {
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
    }
}
