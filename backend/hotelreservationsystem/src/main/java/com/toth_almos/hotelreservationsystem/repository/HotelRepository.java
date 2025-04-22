package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @EntityGraph(attributePaths = {"rooms"})
    @Query("SELECT h FROM Hotel h")
    public List<Hotel> findAll();

    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.rooms WHERE h.id = :id")
    Optional<Hotel> findById(@Param("id") Long id);

    public List<Hotel> findByCountry(String country);
    public List<Hotel> findByNameContainingIgnoreCase(String name);
}
