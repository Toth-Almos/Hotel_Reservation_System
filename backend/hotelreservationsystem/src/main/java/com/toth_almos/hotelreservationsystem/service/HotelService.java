package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.HotelRequest;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    public Hotel findById(Long id);
    public List<Hotel> findAll();
    public Page<Hotel> findFilteredHotels(String name, String country, Integer star, Pageable pageable);
    public List<Hotel> findByCountry(String country);
    public List<Hotel> findByNameContaining(String name);
    public Hotel createHotel(HotelRequest request);
    public Hotel updateHotel(Long id, HotelRequest request);
    public void deleteHotel(Long id);
}
