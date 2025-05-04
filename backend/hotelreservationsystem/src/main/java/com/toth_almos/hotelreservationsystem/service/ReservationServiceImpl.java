package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.ReservationRequest;
import com.toth_almos.hotelreservationsystem.model.*;
import com.toth_almos.hotelreservationsystem.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    public List<Reservation> findActiveReservationsByCustomerId(Long id) {
        return reservationRepository.findActiveReservationsByCustomerId(id, LocalDate.now());
    }

    @Override
    public Page<Reservation> findFilteredReservations(String username, String hotelName, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return reservationRepository.findAll((Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (username != null && !username.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("customer").get("username")), "%" + username.toLowerCase() + "%"));
            }

            if (hotelName != null && !hotelName.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("hotel").get("name")), "%" + hotelName.toLowerCase() + "%"));
            }

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("checkInDate"), startDate));
            }

            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("checkOutDate"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    @Transactional
    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {
        if(reservationRequest.getCheckInDate().isEqual(reservationRequest.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in and Check-out date can not be on the same day.");
        }

        long daysBetween = ChronoUnit.DAYS.between(reservationRequest.getCheckInDate(), reservationRequest.getCheckOutDate());

        if(daysBetween > 30) {
            throw new IllegalArgumentException("Reservations cannot exceed 30 days.");
        }
        if(ChronoUnit.DAYS.between(LocalDate.now(), reservationRequest.getCheckInDate()) <= 4) {
            throw new IllegalArgumentException("You have to make a reservation before 4 days of the check in date at least.");
        }

        Customer customer = userRepository.findByCustomerId(reservationRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Hotel hotel = hotelRepository.findById(reservationRequest.getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        boolean hasDateConflict = reservationRepository.existsByCustomerIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(customer.getId(), reservationRequest.getCheckOutDate(), reservationRequest.getCheckInDate());
        if(hasDateConflict) {
            throw new IllegalStateException("You already have an active reservation within the selected date range.");
        }

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

    @Transactional
    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if(ChronoUnit.DAYS.between(LocalDate.now(), reservation.getCheckInDate()) <= 4) {
            throw new IllegalStateException("Reservations can only be canceled before 4 days at maximum.");
        }

        for (ReservationItem item : reservation.getReservationItems()) {
            reservationItemRepository.delete(item);
        }
        reservationRepository.delete(reservation);
    }


}
