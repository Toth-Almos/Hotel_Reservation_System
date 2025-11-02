package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.CouponRequest;
import com.toth_almos.hotelreservationsystem.model.Coupon;
import com.toth_almos.hotelreservationsystem.repository.CouponRedemptionRepository;
import com.toth_almos.hotelreservationsystem.repository.CouponRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        if(couponRepository.findByCode(request.getCode()).isPresent()) {
            throw new IllegalArgumentException("A coupon with this code is already exists. Please provide a unique code!");
        }

        if(request.getValidFrom().isAfter(request.getValidUntil())) {
            throw new IllegalArgumentException("Coupon's validity starting date can not be after validity end date!");
        }

        Coupon coupon = new Coupon();
        coupon.setCode(request.getCode());
        coupon.setType(request.getType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setActive(request.getIsActive());
        coupon.setValidFrom(request.getValidFrom());
        coupon.setValidUntil(request.getValidUntil());

        return couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Coupon not found with id: " + id));
        coupon.setActive(false);
        couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(CouponRequest request) {
        Coupon existingCoupon = couponRepository.findByCode(request.getCode()).orElseThrow(() -> new EntityNotFoundException("Coupon not found with code: " + request.getCode()));

        if(request.getValidFrom().isAfter(request.getValidUntil())) {
            throw new IllegalArgumentException("Coupon's validity starting date can not be after validity end date!");
        }

        existingCoupon.setCode(request.getCode());
        existingCoupon.setType(request.getType());
        existingCoupon.setDiscountValue(request.getDiscountValue());
        existingCoupon.setActive(request.getIsActive());
        existingCoupon.setValidFrom(request.getValidFrom());
        existingCoupon.setValidUntil(request.getValidUntil());

        return couponRepository.save(existingCoupon);
    }

    @Override
    public Page<Coupon> getAllCoupons(String code, Boolean isActive, Pageable pageable) {
        return couponRepository.findAll((Root<Coupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (code != null && !code.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + code.trim().toLowerCase() + "%"));
            }

            if (isActive != null) {
                predicates.add(cb.equal(root.get("isActive"), isActive));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
