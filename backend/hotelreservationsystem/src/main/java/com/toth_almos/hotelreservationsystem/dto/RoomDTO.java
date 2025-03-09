package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.RoomType;
import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private Long hotelId;
    private RoomType type;
    private int maxGuests;
    private int totalCount;
    private double pricePerNight;
}
