package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public List<Reservation> findByCustomerId(Long userId);
}
