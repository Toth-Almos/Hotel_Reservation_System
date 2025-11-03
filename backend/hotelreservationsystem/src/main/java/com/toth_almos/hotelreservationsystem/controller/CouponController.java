package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.CouponDTO;
import com.toth_almos.hotelreservationsystem.dto.CouponRequest;
import com.toth_almos.hotelreservationsystem.dto.CouponValidationDTO;
import com.toth_almos.hotelreservationsystem.dto.HotelDTO;
import com.toth_almos.hotelreservationsystem.mapper.CouponMapper;
import com.toth_almos.hotelreservationsystem.model.Coupon;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/filtered")
    public Page<CouponDTO> getFilteredCoupons(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Coupon> coupons = couponService.getAllCoupons(code, isActive, pageRequest);

        return coupons.map(couponMapper::toDTO);
    }

    @PostMapping("/create-coupon")
    public CouponDTO createCoupon(@RequestBody CouponRequest request) {
        Coupon coupon = couponService.createCoupon(request);
        return couponMapper.toDTO(coupon);
    }

    @PatchMapping("/update-coupon")
    public CouponDTO updateCoupon(@RequestBody CouponRequest request) {
        Coupon coupon = couponService.updateCoupon(request);
        return couponMapper.toDTO(coupon);
    }

    @DeleteMapping("/delete-coupon/{id}")
    public String deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return "Coupon deleted Successfully!";
    }
}
