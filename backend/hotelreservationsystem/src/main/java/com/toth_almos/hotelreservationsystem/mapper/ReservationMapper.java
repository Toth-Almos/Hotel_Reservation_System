package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.ReservationDTO;
import com.toth_almos.hotelreservationsystem.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReservationItemMapper.class)
@Component
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    //@Mapping(source = "reservationItems", target = "reservationItems")
    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "customer.id", target = "customerId")
    ReservationDTO toDTO(Reservation reservation);

    //@Mapping(source = "reservationItems", target = "reservationItems")
    @Mapping(source = "hotelId", target = "hotel.id")
    @Mapping(source = "customerId", target = "customer.id")
    Reservation toEntity(ReservationDTO reservationDTO);

    List<ReservationDTO> toDTOList(List<Reservation> reservations);
    List<Reservation> toEntityList(List<ReservationDTO> reservationDTOs);
}
