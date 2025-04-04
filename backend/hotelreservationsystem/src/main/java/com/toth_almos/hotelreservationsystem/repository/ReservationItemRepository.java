package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
    public List<ReservationItem> findByReservationId(Long id);
}
