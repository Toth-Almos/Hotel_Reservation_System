package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReviewRequest;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.model.Review;
import com.toth_almos.hotelreservationsystem.repository.HotelRepository;
import com.toth_almos.hotelreservationsystem.repository.ReviewRepository;
import com.toth_almos.hotelreservationsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    @Transactional
    @Override
    public Review createReview(ReviewRequest request) {
        int rating = request.getRating();
        if(rating > 5 || rating < 1) {
            throw new IllegalArgumentException("The rating's numeric values must be between 1 and 5.");
        }

        Customer customer = userRepository.findByCustomerId(request.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Hotel hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        Review review = new Review();
        review.setCustomer(customer);
        review.setHotel(hotel);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        reviewRepository.save(review);
        return review;
    }

    @Transactional
    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }


    @Override
    public List<Review> findByCustomerId(Long id) {
        return reviewRepository.findByCustomerId(id);
    }

    @Override
    public List<Review> findByHotelId(Long id) {
        return reviewRepository.findByHotelId(id);
    }
}
