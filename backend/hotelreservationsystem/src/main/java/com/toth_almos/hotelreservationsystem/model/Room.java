package com.toth_almos.hotelreservationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private int maxGuests;
    private int totalCount;
    private double pricePerNight;
}
