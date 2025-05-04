package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    public Reservation findById(Long id);
    public List<Reservation> findByCustomerId(Long id);
    public List<Reservation> findActiveReservationsByCustomerId(Long id);
    public Page<Reservation> findFilteredReservations(String username, String hotelName, LocalDate startDate, LocalDate endDate, Pageable pageable);
    public Reservation createReservation(ReservationRequest reservationRequest);
    public void deleteReservation(Long reservationId);
}
