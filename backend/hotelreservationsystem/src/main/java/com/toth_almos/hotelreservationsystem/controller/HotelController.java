package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.HotelDTO;
import com.toth_almos.hotelreservationsystem.dto.HotelRequest;
import com.toth_almos.hotelreservationsystem.mapper.HotelMapper;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
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

    @GetMapping("/filtered")
    public Page<HotelDTO> getFilteredHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer star,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<Hotel> hotels = hotelService.findFilteredHotels(name, country, star, PageRequest.of(page, size));
        return hotels.map(hotelMapper::toDTO);
    }

    @PostMapping("/create-hotel")
    public HotelDTO createHotel(@RequestBody HotelRequest request) {
        Hotel hotel = hotelService.createHotel(request);
        return hotelMapper.toDTO(hotel);
    }

    @PatchMapping("/update-hotel/{id}")
    public HotelDTO updateHotel(@PathVariable("id") Long id, @RequestBody HotelRequest request) {
        Hotel hotel = hotelService.updateHotel(id, request);
        return hotelMapper.toDTO(hotel);
    }

    @DeleteMapping("/delete-hotel/{id}")
    public String deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotel(id);
        return "Hotel deleted successfully!";
    }
}
