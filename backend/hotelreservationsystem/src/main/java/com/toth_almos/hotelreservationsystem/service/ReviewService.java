package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReviewRequest;
import com.toth_almos.hotelreservationsystem.model.Review;
import java.util.List;

public interface ReviewService {
    public Review findById(Long id);
    public Review createReview(ReviewRequest request);
    public void deleteReview(Long id);
    public List<Review> findByCustomerId(Long id);
    public List<Review> findByHotelId(Long id);
}
