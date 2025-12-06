package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.HotelSummaryDTO;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface HotelSummaryMapper {
    HotelSummaryMapper INSTANCE = Mappers.getMapper(HotelSummaryMapper.class);

    HotelSummaryDTO toDTO(Hotel hotel);

    List<HotelSummaryDTO> toDTOList(List<Hotel> hotels);
}
