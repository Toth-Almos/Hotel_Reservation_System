package com.toth_almos.hotelreservationsystem.dto;

import com.toth_almos.hotelreservationsystem.model.RoomType;
import lombok.Data;

@Data
public class ReservationItemDTO {
    private RoomType roomType;
    private int numberOfRoomsReserved;
    private double roomCost;
}
