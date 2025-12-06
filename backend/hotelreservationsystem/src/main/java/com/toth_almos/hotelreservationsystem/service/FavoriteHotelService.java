package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.FavoriteHotel;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteHotelService {
    public Page<Hotel> getFavorites(Long customerId, Pageable pageable);
    public FavoriteHotel addFavorite(Long customerId, Long hotelId);
    public void removeFavorite(Long customerId, Long hotelId);
    public boolean isFavorite(Long customerId, Long hotelId);
}
