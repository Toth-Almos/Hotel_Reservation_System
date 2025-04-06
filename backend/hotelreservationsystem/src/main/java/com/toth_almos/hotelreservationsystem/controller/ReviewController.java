package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.ReviewDTO;
import com.toth_almos.hotelreservationsystem.dto.ReviewRequest;
import com.toth_almos.hotelreservationsystem.mapper.ReviewMapper;
import com.toth_almos.hotelreservationsystem.model.Review;
import com.toth_almos.hotelreservationsystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping("/create-review")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewRequest request) {
        Review review = this.reviewService.createReview(request);
        return ResponseEntity.ok(reviewMapper.toDTO(review));
    }

    @DeleteMapping("/delete-review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully!");
    }

    @GetMapping("/get-hotel-reviews/{hotelId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForHotel(@PathVariable("hotelId") Long id) {
        List<Review> reviews = reviewService.findByHotelId(id);
        return ResponseEntity.ok(reviewMapper.toDTOList(reviews));
    }

    @GetMapping("/get-customer-reviews/{customerId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForCustomer(@PathVariable("customerId") Long id) {
        List<Review> reviews = reviewService.findByCustomerId(id);
        return ResponseEntity.ok(reviewMapper.toDTOList(reviews));
    }
}
