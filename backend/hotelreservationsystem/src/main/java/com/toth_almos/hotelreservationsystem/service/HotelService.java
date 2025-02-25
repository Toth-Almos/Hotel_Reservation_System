package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Hotel;
import java.util.List;

public interface HotelService {
    public Hotel findById(Long id);
    public List<Hotel> findAll();
    public List<Hotel> findByCountry(String country);
    public List<Hotel> findByNameContaining(String name);
}
