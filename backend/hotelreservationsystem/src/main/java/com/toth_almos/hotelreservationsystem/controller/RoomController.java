package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.RoomDTO;
import com.toth_almos.hotelreservationsystem.dto.RoomRequest;
import com.toth_almos.hotelreservationsystem.mapper.RoomMapper;
import com.toth_almos.hotelreservationsystem.model.Room;
import com.toth_almos.hotelreservationsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomController(RoomService roomService, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.roomMapper = roomMapper;
    }

    @GetMapping("/{id}")
    public RoomDTO findById(@PathVariable("id") Long id) {
        Room room = roomService.findById(id);
        return roomMapper.toDTO(room);
    }

    @GetMapping("/get-by-hotel/{id}")
    public List<RoomDTO> findByHotelId(@PathVariable("id") Long id) {
        List<Room> rooms = roomService.findByHotelId(id);
        List<RoomDTO> roomDTOs = roomMapper.toDTOList(rooms);
        return rooms.isEmpty() ? null : roomDTOs;
    }

    @PostMapping("/create-room")
    public RoomDTO createRoom(@RequestBody RoomRequest request) {
        Room room = roomService.createRoom(request);
        return roomMapper.toDTO(room);
    }

    @PatchMapping("/update/{id}")
    public RoomDTO updateRoom(@PathVariable("id") Long id, @RequestBody RoomRequest request) {
        Room room = roomService.updateRoom(id, request);
        return roomMapper.toDTO(room);
    }

    @DeleteMapping("/delete-room/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {
        roomService.deleteRoom(id);
        return "Room deleted successfully!";
    }
}
