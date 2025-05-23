package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.PaymentMethod;
import com.toth_almos.hotelreservationsystem.model.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private PaymentMethod method;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
}
