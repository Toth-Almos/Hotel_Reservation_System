package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findById(@PathVariable("id") Long id) {
        Hotel hotel = hotelService.findById(id);    //exception is handled my the GlobalExceptionHandler -> returns 404 if not found
        return ResponseEntity.ok(hotel);
    }

    @GetMapping()
    public ResponseEntity<List<Hotel>> findAll() {
        List<Hotel> hotels = hotelService.findAll();
        return hotels.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotels);
    }
}
