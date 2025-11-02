package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.CouponType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponRequest {
    private String code;
    private CouponType type;
    private int discountValue;
    private boolean isActive;
    private LocalDate validFrom;
    private LocalDate validUntil;

    public boolean getIsActive() {
        return this.isActive;
    }
}
