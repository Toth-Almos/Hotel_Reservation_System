package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

@Data
public class HotelSummaryDTO {
    private Long id;
    private String name;
    private String city;
    private String country;
    private int star;
}
