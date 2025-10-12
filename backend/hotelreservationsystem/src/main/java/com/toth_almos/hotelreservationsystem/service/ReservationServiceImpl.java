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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final PaymentRepository paymentRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository, ReservationItemRepository reservationItemRepository, PaymentRepository paymentRepository, JavaMailSender mailSender) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationItemRepository = reservationItemRepository;
        this.paymentRepository = paymentRepository;
        this.mailSender = mailSender;
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
        if(reservationRequest.getCheckInDate().isAfter(reservationRequest.getCheckOutDate())) {
            throw new IllegalArgumentException("The check-in date can not be after the check-out date! Please add a valid time duration!");
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

        //Create Payment:
        Payment payment = new Payment();
        payment.setMethod(reservationRequest.getPaymentMethod());
        if(reservationRequest.getPaymentMethod().equals(PaymentMethod.ONLINE)) {
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.APPROVED);
        }
        else {
            payment.setPaymentDate(null);
            payment.setStatus(PaymentStatus.PENDING);
        }
        paymentRepository.save(payment);
        reservation.setPayment(payment);

        //Send email
        String subject = "Reservation created successfully";
        String intro = "Dear "+ customer.getUsername() + ",\n" + "Your reservation was successfully created! If it wasn't you then please contact one of our administrators! You can see the details below:\n\n";
        String outro = "\n\nIf you want to cancel your reservation, you can do it on our website in your your profile at the Cancel Active Reservations tab. Please keep in mind that you can only cancel your reservation 4 days before your check-in date for free. Otherwise you can contact one of our administrators who can cancel your reservations.";

        StringBuilder rooms = new StringBuilder();
        for(ReservationItem items : reservationItems) {
            rooms.append("\tType of room: " + items.getRoom().getType().toString() + " - " + items.getNumberOfRoomsReserved() + " room(s)" + " - " + items.getRoom().getPricePerNight() + " €/night\n");
        }

        String details = "The hotel: " + hotel.getName() + " " + hotel.getAddress() +
                "\nThe room(s) you reserved:\n" + rooms +
                "\nYour check-in date: " + reservationRequest.getCheckInDate() +
                "\nYour check-out date: " + reservationRequest.getCheckOutDate() +
                "\nTotal cost of the stay: " + totalCost + "€" +
                "\nPayment method chosen: " + reservationRequest.getPaymentMethod();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject(subject);
        message.setText(intro + details + outro);
        message.setFrom("toth.2000.almos@gmail.com");
        mailSender.send(message);

        return reservationRepository.save(reservation);
    }

    @Transactional
    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if(ChronoUnit.DAYS.between(LocalDate.now(), reservation.getCheckInDate()) <= 4) {
            throw new IllegalStateException("Reservations can only be canceled before 4 days at maximum. Please contact the Administrator for more information!");
        }

        //Send email
        String subject = "Reservation cancelled successfully";
        String intro = "Dear "+ reservation.getCustomer().getUsername() + ",\n\n" + "Your reservation was successfully cancelled! If it wasn't you then please contact one of our administrators!\n";
        String outro = "We are sorry that you've changed your mind. If there was anything wrong with our system please contact one of our administrators! If there was something wrong with the hotel of your choice please contact the hotel staff!\n\nWe hope that you'll pay us a visit soon!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservation.getCustomer().getEmail());
        message.setSubject(subject);
        message.setText(intro + outro);
        message.setFrom("toth.2000.almos@gmail.com");
        mailSender.send(message);

        for (ReservationItem item : reservation.getReservationItems()) {
            reservationItemRepository.delete(item);
        }
        reservationRepository.delete(reservation);
    }

    @Override
    @Transactional
    public Reservation upddateReservation(Long reservationId, ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        // Only update dates if they're changing and valid
        if (!request.getCheckInDate().isEqual(request.getCheckOutDate())) {
            long daysBetween = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());

            if (daysBetween > 30) {
                throw new IllegalArgumentException("Reservations cannot exceed 30 days.");
            }

            if (ChronoUnit.DAYS.between(LocalDate.now(), request.getCheckInDate()) <= 4) {
                throw new IllegalArgumentException("You have to make a reservation before 4 days of the check in date at least.");
            }

            // Check for conflicts if dates changed
            if (!reservation.getCheckInDate().equals(request.getCheckInDate()) || !reservation.getCheckOutDate().equals(request.getCheckOutDate())) {
                boolean conflict = reservationRepository
                        .existsByCustomerIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqualAndIdNot(
                                reservation.getCustomer().getId(),
                                request.getCheckOutDate(),
                                request.getCheckInDate(),
                                reservation.getId()
                        );

                if (conflict) {
                    throw new IllegalStateException("You already have an active reservation within the selected date range.");
                }
            }

            reservation.setCheckInDate(request.getCheckInDate());
            reservation.setCheckOutDate(request.getCheckOutDate());
        }

        // Update total cost if needed
        if (request.getTotalCost() != reservation.getTotalCost()) {
            reservation.setTotalCost(request.getTotalCost());
        }

        return reservationRepository.save(reservation);
    }


}
