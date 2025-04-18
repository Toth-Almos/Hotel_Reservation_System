package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private Long customerId;
    private Long hotelId;
    private String customerName;
    private String hotelName;
    private int rating;
    private String comment;
}
