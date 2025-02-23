package com.toth_almos.hotelreservationsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "customer")
public class Customer extends User {
    private String email;
    private String phoneNumber;
    private String address;
}
