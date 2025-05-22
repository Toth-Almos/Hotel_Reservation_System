package com.toth_almos.hotelreservationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class VerificationToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime expiry = LocalDateTime.now().plusHours(24);

    protected VerificationToken() {}
    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
