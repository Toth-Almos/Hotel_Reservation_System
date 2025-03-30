package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationDTO {
    private Long id;
    private Long customerId;
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate reservationDate;
    private double totalCost;
    private List<ReservationItemDTO> reservationItems;
}
