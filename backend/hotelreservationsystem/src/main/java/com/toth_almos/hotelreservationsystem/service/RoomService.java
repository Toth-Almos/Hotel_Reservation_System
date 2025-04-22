package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.RoomRequest;
import com.toth_almos.hotelreservationsystem.model.Room;
import java.util.List;

public interface RoomService {
    public Room findById(Long id);
    public List<Room> findByHotelId(Long hotelId);
    public Room createRoom(RoomRequest request);
    public Room updateRoom(Long id, RoomRequest request);
    public void deleteRoom(Long id);
}
