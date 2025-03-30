package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.ReservationDTO;
import com.toth_almos.hotelreservationsystem.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "reservationItems", target = "reservationItems")
    ReservationDTO toDTO(Reservation reservation);

    @Mapping(source = "reservationItems", target = "reservationItems")
    Reservation toEntity(ReservationDTO reservationDTO);

    List<ReservationDTO> toDTOList(List<Reservation> reservations);
    List<Reservation> toEntityList(List<ReservationDTO> reservationDTOs);
}
