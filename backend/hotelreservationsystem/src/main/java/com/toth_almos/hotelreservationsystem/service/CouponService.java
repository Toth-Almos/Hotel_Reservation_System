package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.CouponRequest;
import com.toth_almos.hotelreservationsystem.model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {
    public Coupon validateCouponCode(String code, Long customerId);
    public Coupon createCoupon(CouponRequest request);
    public void deleteCoupon(Long id);
    public Coupon updateCoupon(CouponRequest request);
    public Page<Coupon> getAllCoupons(String code, Boolean isActive, Pageable pageable);
}
