package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.CouponRedemption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRedemptionRepository extends JpaRepository<CouponRedemption, Long> {
    boolean existsByCouponIdAndCustomerId(Long couponId, Long customerId);
}
