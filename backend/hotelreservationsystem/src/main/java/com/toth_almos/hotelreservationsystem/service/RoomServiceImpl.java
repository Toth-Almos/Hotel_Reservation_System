package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.RoomRequest;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.model.Room;
import com.toth_almos.hotelreservationsystem.repository.HotelRepository;
import com.toth_almos.hotelreservationsystem.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
    }

    @Override
    public List<Room> findByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Override
    public Room createRoom(RoomRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + request.getHotelId()));

        if(request.getMaxGuests() < 1 || request.getTotalCount() < 1 || request.getPricePerNight() < 1) {
            throw new IllegalArgumentException("A Room's maxGuest, totalCount and pricePerNight attributes can not be less then 1.");
        }

        Room newRoom = new Room();
        newRoom.setHotel(hotel);
        newRoom.setType(request.getType());
        newRoom.setMaxGuests(request.getMaxGuests());
        newRoom.setTotalCount(request.getTotalCount());
        newRoom.setPricePerNight(request.getPricePerNight());

        roomRepository.save(newRoom);
        return newRoom;
    }

    @Override
    public Room updateRoom(Long id, RoomRequest request) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + request.getHotelId()));

        if(request.getMaxGuests() < 1 || request.getTotalCount() < 1 || request.getPricePerNight() < 1) {
            throw new IllegalArgumentException("A Room's maxGuest, totalCount and pricePerNight attributes can not be less then 1.");
        }

        room.setHotel(hotel);
        room.setType(request.getType());
        room.setMaxGuests(request.getMaxGuests());
        room.setTotalCount(request.getTotalCount());
        room.setPricePerNight(request.getPricePerNight());

        roomRepository.save(room);
        return room;
    }
}
