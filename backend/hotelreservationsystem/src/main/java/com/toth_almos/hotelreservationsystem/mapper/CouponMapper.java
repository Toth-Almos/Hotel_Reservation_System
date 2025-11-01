package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.CouponDTO;
import com.toth_almos.hotelreservationsystem.model.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
@Component
public interface CouponMapper {
    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    @Mapping(source = "redemptions", target = "redemptions")
    CouponDTO toDTO(Coupon coupon);

    @Mapping(source = "redemptions", target = "redemptions")
    Coupon toEntity(CouponDTO couponDTO);

    List<CouponDTO> toDTOList(List<Coupon> hotels);
    List<Coupon> toEntityList(List<CouponDTO> hotelDTOs);
}
