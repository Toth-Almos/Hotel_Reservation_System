package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.model.Room;
import com.toth_almos.hotelreservationsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin("*")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable("id") Long id) {
        Room room = roomService.findById(id);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/hotel/rooms/{id}")
    public ResponseEntity<List<Room>> findByHotelId(@PathVariable("id") Long id) {
        List<Room> rooms = roomService.findByHotelId(id);
        return rooms.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rooms);
    }
}
