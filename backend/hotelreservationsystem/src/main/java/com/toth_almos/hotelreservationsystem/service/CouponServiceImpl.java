package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.CouponRequest;
import com.toth_almos.hotelreservationsystem.model.Coupon;
import com.toth_almos.hotelreservationsystem.repository.CouponRedemptionRepository;
import com.toth_almos.hotelreservationsystem.repository.CouponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final CouponRedemptionRepository couponRedemptionRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository, CouponRedemptionRepository couponRedemptionRepository) {
        this.couponRepository = couponRepository;
        this.couponRedemptionRepository = couponRedemptionRepository;
    }

    @Override
    public Coupon validateCouponCode(String code, Long customerId) {
        Coupon couponToValidate = couponRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Coupon not found with code: " + code));

        if(!couponToValidate.isActive()) {
            throw new IllegalArgumentException("Coupon is not active or it is deleted!");
        }

        if(LocalDate.now().isBefore(couponToValidate.getValidFrom()) || LocalDate.now().isAfter(couponToValidate.getValidUntil())) {
            throw new IllegalArgumentException("The coupon's validity is expired!");
        }

        if (couponRedemptionRepository.existsByCouponIdAndCustomerId(couponToValidate.getId(), customerId)) {
            throw new IllegalArgumentException("You’ve already used this coupon.");
        }

        return couponToValidate;
    }

    @Override
    public Coupon createCoupon(CouponRequest request) {
        return null;
    }

    @Override
    public void deleteCoupon(Long id) {

    }

    @Override
    public List<Coupon> getAllCoupons() {
        return List.of();
    }

    @Override
    public Coupon getCouponById(Long id) {
        return null;
    }
}
