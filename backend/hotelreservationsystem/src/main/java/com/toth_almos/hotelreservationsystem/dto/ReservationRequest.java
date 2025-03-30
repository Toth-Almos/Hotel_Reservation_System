package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationRequest {
    private Long hotelId;
    private Long customerId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private List<ReservationItemRequest> reservationItemRequests;
}
