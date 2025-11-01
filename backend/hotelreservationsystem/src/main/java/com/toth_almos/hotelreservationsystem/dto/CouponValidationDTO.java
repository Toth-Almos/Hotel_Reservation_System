package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.CouponType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponValidationDTO {
    private boolean valid;
    private String message;

    private String code;
    private CouponType type;
    private int discountValue;
    private LocalDate validFrom;
    private LocalDate validUntil;
}
