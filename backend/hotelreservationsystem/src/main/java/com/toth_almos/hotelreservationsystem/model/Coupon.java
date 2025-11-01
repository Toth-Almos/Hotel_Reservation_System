package com.toth_almos.hotelreservationsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType type;

    @Column(nullable = false)
    private int discountValue;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false)
    private LocalDate validUntil;

    @OneToMany(mappedBy = "coupon")
    @Column(nullable = false)
    private List<CouponRedemption> redemptions = new ArrayList<>();
}
