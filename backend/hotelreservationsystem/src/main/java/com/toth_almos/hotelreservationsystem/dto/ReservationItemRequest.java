package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class ReservationItemRequest {
    private Long roomId;
    private double pricePerNight;
    private int numberOfRooms;
}
