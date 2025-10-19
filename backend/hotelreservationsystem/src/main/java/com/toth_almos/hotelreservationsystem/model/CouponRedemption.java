package com.toth_almos.hotelreservationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(
        name = "coupon_redemption",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"coupon_id", "customer_id"}),  //one coupon per customer
                @UniqueConstraint(columnNames = {"reservation_id"})             //one coupon per reservation
        }
)
public class CouponRedemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDate redeemedAt = LocalDate.now();
}
