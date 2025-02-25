package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.ReservationItem;
import java.util.List;

public interface ReservationItemService {
    public ReservationItem findById(Long id);
    public List<ReservationItem> findByReservationId(Long id);
}
