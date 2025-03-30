package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.ReservationDTO;
import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reservation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create-reservation")
    public ResponseEntity<ReservationDTO> createReservation(ReservationRequest reservationRequest) {
        //TODO
        return ResponseEntity.ok(new ReservationDTO());
    }
}
