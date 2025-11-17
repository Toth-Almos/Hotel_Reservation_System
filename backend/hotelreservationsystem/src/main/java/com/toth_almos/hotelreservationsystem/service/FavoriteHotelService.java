package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Hotel;

import java.util.List;

public interface FavoriteHotelService {
    public List<Hotel> getFavorites(Long customerId);
    public void addFavorite(Long customerId, Long hotelId);
    public void removeFavorite(Long customerId, Long hotelId);
}
