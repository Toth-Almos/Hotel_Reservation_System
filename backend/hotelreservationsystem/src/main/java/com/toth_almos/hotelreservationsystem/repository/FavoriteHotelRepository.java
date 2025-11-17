package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.FavoriteHotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteHotelRepository extends JpaRepository<FavoriteHotel, Long> {
    List<FavoriteHotel> findByCustomerId(Long customerId);
    Optional<FavoriteHotel> findByCustomerIdAndHotelId(Long customerId, Long hotelId);
}
