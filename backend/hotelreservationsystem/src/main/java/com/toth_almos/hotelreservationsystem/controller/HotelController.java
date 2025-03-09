package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.HotelDTO;
import com.toth_almos.hotelreservationsystem.mapper.HotelMapper;
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
    private final HotelMapper hotelMapper;

    @Autowired
    public HotelController(HotelService hotelService, HotelMapper hotelMapper) {
        this.hotelService = hotelService;
        this.hotelMapper = hotelMapper;
    }

    @GetMapping("/{id}")
    public HotelDTO findById(@PathVariable("id") Long id) {
        Hotel hotel = hotelService.findById(id);    //exception is handled my the GlobalExceptionHandler -> returns 404 if not found
        return hotelMapper.toDTO(hotel);
    }

    @GetMapping()
    public List<HotelDTO> findAll() {
        List<Hotel> hotels = hotelService.findAll();
        List<HotelDTO> hotelDTOs= hotelMapper.toDTOList(hotels);
        return hotelDTOs.isEmpty() ? null : hotelDTOs;
    }
}
