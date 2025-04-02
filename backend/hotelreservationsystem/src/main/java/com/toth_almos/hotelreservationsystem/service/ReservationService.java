package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.model.Reservation;

import java.util.List;

public interface ReservationService {
    public Reservation findById(Long id);
    public List<Reservation> findByCustomerId(Long id);
    public List<Reservation> findActiveReservationsByCustomerId(Long id);
    public Reservation createReservation(ReservationRequest reservationRequest);
    public void deleteReservation(Long reservationId);
}
