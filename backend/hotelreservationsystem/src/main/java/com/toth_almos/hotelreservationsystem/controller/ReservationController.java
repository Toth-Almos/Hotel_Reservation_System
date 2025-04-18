package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.ReservationDTO;
import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.mapper.ReservationMapper;
import com.toth_almos.hotelreservationsystem.model.Reservation;
import com.toth_almos.hotelreservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservation")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping("/create-reservation")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok(reservationMapper.toDTO(reservation));
    }

    @DeleteMapping("/delete-reservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Successfully deleted reservation.");
    }

    @GetMapping("/get-reservations/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllReservationsForCustomer(@PathVariable("id") Long id) {
        List<Reservation> reservations = reservationService.findByCustomerId(id);
        return ResponseEntity.ok(reservationMapper.toDTOList(reservations));
    }

    @GetMapping("/get-active-reservations/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllActiveReservationsForCustomer(@PathVariable("id") Long id) {
        List<Reservation> reservations = reservationService.findActiveReservationsByCustomerId(id);
        return ResponseEntity.ok(reservationMapper.toDTOList(reservations));
    }
}
