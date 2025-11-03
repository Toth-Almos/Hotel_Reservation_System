package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.CouponRedemption;
import com.toth_almos.hotelreservationsystem.model.CouponType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CouponDTO {
    private Long id;
    private String code;
    private CouponType type;
    private int discountValue;
    private boolean isActive;
    private LocalDate validFrom;
    private LocalDate validUntil;
}
