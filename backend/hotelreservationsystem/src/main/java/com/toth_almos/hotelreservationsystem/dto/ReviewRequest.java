package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long customerId;
    private Long hotelId;
    private int rating;
    private String comment;
}
