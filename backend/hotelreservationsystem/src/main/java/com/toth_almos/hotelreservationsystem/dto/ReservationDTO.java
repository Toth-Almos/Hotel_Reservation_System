package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.PaymentMethod;
import com.toth_almos.hotelreservationsystem.model.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationDTO {
    private Long id;
    private Long customerId;
    private Long hotelId;
    private Long paymentId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate reservationDate;
    private double totalCost;
    private List<ReservationItemDTO> reservationItems;
}
