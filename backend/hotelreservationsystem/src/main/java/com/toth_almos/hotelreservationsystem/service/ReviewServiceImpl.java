package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReviewRequest;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.model.Reservation;
import com.toth_almos.hotelreservationsystem.model.Review;
import com.toth_almos.hotelreservationsystem.repository.HotelRepository;
import com.toth_almos.hotelreservationsystem.repository.ReservationRepository;
import com.toth_almos.hotelreservationsystem.repository.ReviewRepository;
import com.toth_almos.hotelreservationsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, HotelRepository hotelRepository, ReservationRepository reservationRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.reservationRepository = reservationRepository;
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

        List<Review> pastReviews = reviewRepository.findByCustomerId(request.getCustomerId());
        for(Review review : pastReviews) {
            if(Objects.equals(review.getHotel().getId(), request.getHotelId())) {
                throw new IllegalArgumentException("You can only leave one review per hotel.");
            }
        }

        boolean reviewEligible = false;
        List<Reservation> reservations = reservationRepository.findByCustomerId(request.getCustomerId());
        for(Reservation res : reservations) {
            if(Objects.equals(res.getHotel().getId(), request.getHotelId()) && LocalDate.now().isAfter(res.getCheckOutDate())) {
                reviewEligible = true;
                break;
            }
        }
        if(!reviewEligible) {
            throw new IllegalArgumentException("You can't rate a hotel in which you were not a quest.");
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
