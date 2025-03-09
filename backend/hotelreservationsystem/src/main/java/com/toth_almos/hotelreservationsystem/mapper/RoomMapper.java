package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.RoomDTO;
import com.toth_almos.hotelreservationsystem.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomDTO toDTO(Room room);

    @Mapping(source = "hotelId", target = "hotel.id")
    Room toEntity(RoomDTO roomDTO);

    List<RoomDTO> toDTOList(List<Room> rooms);
    List<Room> toEntityList(List<RoomDTO> roomDTOs);
}
