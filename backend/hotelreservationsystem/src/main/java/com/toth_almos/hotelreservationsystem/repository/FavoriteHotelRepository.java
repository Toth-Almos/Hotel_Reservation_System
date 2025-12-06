package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.FavoriteHotel;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavoriteHotelRepository extends JpaRepository<FavoriteHotel, Long> {
    @Query("""
    select fh.hotel
    from FavoriteHotel fh
    where fh.customer.id = :customerId
    """)
    Page<Hotel> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
    Optional<FavoriteHotel> findByCustomerIdAndHotelId(Long customerId, Long hotelId);
    boolean existsByCustomerIdAndHotelId(Long customerId, Long hotelId);
}
