package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.PaymentMethod;
import com.toth_almos.hotelreservationsystem.model.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationRequest {
    private Long hotelId;
    private Long customerId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalCost;
    private List<ReservationItemRequest> reservationItemRequests;
    private PaymentMethod paymentMethod;
    private String couponCode;
}
