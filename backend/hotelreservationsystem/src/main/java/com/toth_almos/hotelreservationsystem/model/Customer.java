package com.toth_almos.hotelreservationsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@DiscriminatorValue("customer")
public class Customer extends User {
    private String email;
    private String phoneNumber;
    private String address;
}
