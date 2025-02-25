package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Reservation;
import java.util.List;

public interface ReservationService {
    public Reservation findById(Long id);
    public List<Reservation> findByUserId(Long id);
}
