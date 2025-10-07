package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.HotelRequest;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.model.Room;
import com.toth_almos.hotelreservationsystem.repository.HotelRepository;
import com.toth_almos.hotelreservationsystem.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> findFilteredHotels(String name, String country, Integer star, Pageable pageable) {
        return hotelRepository.findAll((Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isFalse(root.get("deleted")));

            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }

            if (country != null && !country.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("country")), "%" + country.trim().toLowerCase() + "%"));
            }

            if (star != null && star > 0 ) {
                predicates.add(cb.equal(root.get("star"), star));
            }

            pageable.getSort().forEach(order -> {
                Path<Object> path = root.get(order.getProperty());
                query.orderBy(order.isAscending() ? cb.asc(path) : cb.desc(path));
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    @Override
    public List<Hotel> findByCountry(String country) {
        return hotelRepository.findByCountryAndDeletedFalse(country);
    }

    @Override
    public List<Hotel> findByNameContaining(String name) {
        return hotelRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name);
    }

    @Override
    public Hotel createHotel(HotelRequest request) {
        if(request.getStar() < 1 || request.getStar() > 5) {
            throw new IllegalArgumentException("Hotel rating must be between 1 and 5");
        }

        if(request.getName().length() < 4 || request.getCountry().length() < 4 || request.getCity().length() < 4 || request.getAddress().length() < 10) {
            throw new IllegalArgumentException("Please give valid value for the Hotel's name/country/city/address");
        }

        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setStar(request.getStar());
        hotel.setCity(request.getCity());
        hotel.setCountry(request.getCountry());
        hotel.setAddress(request.getAddress());
        hotel.setStar(request.getStar());
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Long id, HotelRequest request) {
        Hotel existingHotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));

        if(request.getStar() < 1 || request.getStar() > 5) {
            throw new IllegalArgumentException("Hotel rating must be between 1 and 5");
        }

        if(request.getName().length() < 4 || request.getCountry().length() < 4 || request.getCity().length() < 4 || request.getAddress().length() < 10) {
            throw new IllegalArgumentException("Please give valid value for the Hotel's name/country/city/address");
        }

        existingHotel.setName(request.getName());
        existingHotel.setCountry(request.getCountry());
        existingHotel.setCity(request.getCity());
        existingHotel.setAddress(request.getAddress());
        existingHotel.setStar(request.getStar());
        return hotelRepository.save(existingHotel);
    }

    @Override
    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + id));

        for(Room room : hotel.getRooms()) {
            room.setDeleted(true);
            roomRepository.save(room);
        }

        hotel.setDeleted(true);
        hotelRepository.save(hotel);
    }
}
