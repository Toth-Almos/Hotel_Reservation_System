package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    public List<Hotel> findByCountry(String country);
    public List<Hotel> findByNameContainingIgnoreCase(String name);
}
