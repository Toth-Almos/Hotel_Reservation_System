package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.ReservationItemDTO;
import com.toth_almos.hotelreservationsystem.model.ReservationItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ReservationItemMapper {
    ReservationItemMapper INSTANCE = Mappers.getMapper(ReservationItemMapper.class);

    @Mapping(source = "room.type", target = "roomType")
    @Mapping(source = "room.pricePerNight", target = "roomCost")
    ReservationItemDTO toDTO(ReservationItem reservationItem);

    @Mapping(source = "roomType", target = "room.type")
    @Mapping(source = "roomCost", target = "room.pricePerNight")
    ReservationItem toEntity(ReservationItemDTO reservationDTO);

    List<ReservationItemDTO> toDTOList(List<ReservationItem> reservationItems);
    List<ReservationItem> toEntityList(List<ReservationItemDTO> reservationItemDTOs);
}
