package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @EntityGraph(attributePaths = {"reservationItems", "reservationItems.room"})
    List<Reservation> findByCustomerId(Long userId);
    boolean existsByCustomerIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(Long customerId, LocalDate checkOutDate, LocalDate checkInDate);

    @EntityGraph(attributePaths = {"reservationItems", "reservationItems.room"})
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId AND r.checkOutDate > :currentDate")
    List<Reservation> findActiveReservationsByCustomerId(@Param("customerId") Long customerId, @Param("currentDate") LocalDate currentDate);
}
