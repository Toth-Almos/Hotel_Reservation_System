package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> findByCountry(String country) {
        return hotelRepository.findByCountry(country);
    }

    @Override
    public List<Hotel> findByNameContaining(String name) {
        return hotelRepository.findByNameContainingIgnoreCase(name);
    }
}
