package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.HotelSummaryDTO;
import com.toth_almos.hotelreservationsystem.mapper.HotelMapper;
import com.toth_almos.hotelreservationsystem.mapper.HotelSummaryMapper;
import com.toth_almos.hotelreservationsystem.model.FavoriteHotel;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.service.FavoriteHotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/favorites")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FavoriteHotelController {

    private final FavoriteHotelService favoriteHotelService;
    private final HotelSummaryMapper hotelSummaryMapper;

    public FavoriteHotelController(FavoriteHotelService favoriteHotelService, HotelMapper hotelMapper, HotelSummaryMapper hotelSummaryMapper) {
        this.favoriteHotelService = favoriteHotelService;
        this.hotelSummaryMapper = hotelSummaryMapper;
    }

    @GetMapping("/{customerId}")
    public Page<HotelSummaryDTO> getFavorites(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotels = favoriteHotelService.getFavorites(customerId, pageable);

        return hotels.map(hotelSummaryMapper::toDTO);
    }

    @PostMapping("/add")
    public FavoriteHotel addFavorite(@RequestParam Long customerId, @RequestParam Long hotelId) {
        return favoriteHotelService.addFavorite(customerId, hotelId);
    }

    @DeleteMapping("/remove")
    public String removeFavorite(@RequestParam Long customerId, @RequestParam Long hotelId) {
        favoriteHotelService.removeFavorite(customerId, hotelId);
        return "Hotel successfully removed form your favorite list!";
    }

    @GetMapping("/check")
    public boolean checkIfFavorite(@RequestParam Long customerId, @RequestParam Long hotelId) {
        return favoriteHotelService.isFavorite(customerId, hotelId);
    }
}
