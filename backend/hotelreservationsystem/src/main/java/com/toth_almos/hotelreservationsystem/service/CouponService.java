package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.CouponRequest;
import com.toth_almos.hotelreservationsystem.model.Coupon;

import java.util.List;

public interface CouponService {
    public Coupon validateCouponCode(String code, Long customerId);
    public Coupon createCoupon(CouponRequest request);
    public void deleteCoupon(Long id);
    public List<Coupon> getAllCoupons();
    public Coupon getCouponById(Long id);
}
