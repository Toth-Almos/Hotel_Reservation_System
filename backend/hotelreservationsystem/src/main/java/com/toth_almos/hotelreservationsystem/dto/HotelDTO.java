package com.toth_almos.hotelreservationsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private int star;
    private String country;
    private String city;
    private String address;
    private List<RoomDTO> rooms;
    private boolean deleted;
}
