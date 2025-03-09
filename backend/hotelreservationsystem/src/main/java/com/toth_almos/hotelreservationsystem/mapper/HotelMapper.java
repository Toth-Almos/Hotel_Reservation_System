package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.HotelDTO;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
@Component
public interface HotelMapper {
    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);

    @Mapping(source = "rooms", target = "rooms")
    HotelDTO toDTO(Hotel hotel);

    @Mapping(source = "rooms", target = "rooms")
    Hotel toEntity(HotelDTO hotelDTO);

    List<HotelDTO> toDTOList(List<Hotel> hotels);
    List<Hotel> toEntityList(List<HotelDTO> hotelDTOs);
}
