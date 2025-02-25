package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.model.Review;
import java.util.List;

public interface ReviewService {
    public Review findById(Long id);
    public List<Review> findByHotelId(Long id);
}
