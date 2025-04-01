package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReservationItemRequest;
import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.model.*;
import com.toth_almos.hotelreservationsystem.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationItemRepository reservationItemRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository, ReservationItemRepository reservationItemRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationItemRepository = reservationItemRepository;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + id));
    }

    @Override
    public List<Reservation> findByCustomerId(Long id) {
        return reservationRepository.findByCustomerId(id);
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {
        long daysBetween = ChronoUnit.DAYS.between(reservationRequest.getCheckInDate(), reservationRequest.getCheckOutDate());

        if(daysBetween > 30) {
            throw new IllegalArgumentException("Reservations cannot exceed 30 days.");
        }
        if(ChronoUnit.DAYS.between(reservationRequest.getCheckInDate(), LocalDate.now()) > 4) {
            throw new IllegalArgumentException("You have to make a reservation before 4 days of the check in date.");
        }

        Customer customer = userRepository.findByCustomerId(reservationRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Hotel hotel = hotelRepository.findById(reservationRequest.getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setHotel(hotel);
        reservation.setCheckInDate(reservationRequest.getCheckInDate());
        reservation.setCheckOutDate(reservationRequest.getCheckOutDate());
        reservation.setReservationDate(LocalDate.now());

        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        List<ReservationItem> reservationItems = reservationRequest.getReservationItemRequests().stream()
                .map(itemRequest -> {
                    Room room = roomRepository.findById(itemRequest.getRoomId()).orElseThrow(() -> new EntityNotFoundException("Room not found"));

                    ReservationItem item = new ReservationItem();
                    item.setReservation(reservation);
                    item.setRoom(room);
                    item.setNumberOfRoomsReserved(itemRequest.getNumberOfRooms());

                    double itemCost = room.getPricePerNight() * itemRequest.getNumberOfRooms() * daysBetween;
                    totalCost.updateAndGet(v -> v + itemCost);

                    return item;
                })
                .toList();
        reservation.setTotalCost(totalCost.get());
        reservation.setReservationItems(reservationItems);

        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        for (ReservationItem item : reservation.getReservationItems()) {
            reservationItemRepository.delete(item);
        }
        reservationRepository.delete(reservation);
    }


}
