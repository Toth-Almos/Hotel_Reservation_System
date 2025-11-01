package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.CouponValidationDTO;
import com.toth_almos.hotelreservationsystem.mapper.CouponMapper;
import com.toth_almos.hotelreservationsystem.model.Coupon;
import com.toth_almos.hotelreservationsystem.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/coupons")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @Autowired
    public CouponController(CouponService couponService, CouponMapper couponMapper) {
        this.couponService = couponService;
        this.couponMapper = couponMapper;
    }

    @GetMapping("/validate")
    public CouponValidationDTO validateCoupon(
            @RequestParam String code,
            @RequestParam Long customerId
    ) {
        CouponValidationDTO response = new CouponValidationDTO();
        try {
            Coupon coupon = couponService.validateCouponCode(code, customerId);    //returns coupon object if it's valid and throws exception if not valid
            response.setValid(true);
            response.setMessage("Coupon is valid!");
            response.setCode(coupon.getCode());
            response.setType(coupon.getType());
            response.setDiscountValue(coupon.getDiscountValue());
            response.setValidFrom(coupon.getValidFrom());
            response.setValidUntil(coupon.getValidUntil());
        }
        catch (EntityNotFoundException | IllegalArgumentException ex) {
            response.setValid(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }
}
