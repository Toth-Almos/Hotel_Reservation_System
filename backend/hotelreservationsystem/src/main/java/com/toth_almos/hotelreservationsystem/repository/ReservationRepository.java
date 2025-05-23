package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    @EntityGraph(attributePaths = {"reservationItems", "reservationItems.room", "payment"})
    List<Reservation> findByCustomerId(Long userId);
    boolean existsByCustomerIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(Long customerId, LocalDate checkOutDate, LocalDate checkInDate);

    @EntityGraph(attributePaths = {"reservationItems", "reservationItems.room", "payment"})
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId AND r.checkOutDate > :currentDate")
    List<Reservation> findActiveReservationsByCustomerId(@Param("customerId") Long customerId, @Param("currentDate") LocalDate currentDate);

    @EntityGraph(attributePaths = {"reservationItems", "reservationItems.room", "payment", "customer", "hotel"})
    Page<Reservation> findAll(Specification<Reservation> spec, Pageable pageable);
}
