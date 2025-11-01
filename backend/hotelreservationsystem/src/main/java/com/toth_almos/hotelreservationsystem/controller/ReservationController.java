package com.toth_almos.hotelreservationsystem.controller;

import com.toth_almos.hotelreservationsystem.dto.ReservationDTO;
import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.mapper.ReservationMapper;
import com.toth_almos.hotelreservationsystem.model.Reservation;
import com.toth_almos.hotelreservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/get-filtered-reservations")
    public ResponseEntity<Page<ReservationDTO>> getFilteredReservations(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String hotelName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "checkInDate") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        if (username != null && username.trim().isEmpty()) username = null;
        if (hotelName != null && hotelName.trim().isEmpty()) hotelName = null;
        Page<Reservation> reservations = reservationService.findFilteredReservations(username, hotelName, startDate, endDate, pageable);

        Page<ReservationDTO> reservationDTOs = reservations.map(reservationMapper::toDTO);
        return ResponseEntity.ok(reservationDTOs);
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

    @PatchMapping("/update-reservation/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable("id") Long id, @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.upddateReservation(id, request);
        return ResponseEntity.ok(reservationMapper.toDTO(reservation));
    }
}
