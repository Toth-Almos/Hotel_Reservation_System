package com.toth_almos.hotelreservationsystem.service;

import com.toth_almos.hotelreservationsystem.dto.HotelDTO;
import com.toth_almos.hotelreservationsystem.mapper.HotelMapper;
import com.toth_almos.hotelreservationsystem.model.Customer;
import com.toth_almos.hotelreservationsystem.model.FavoriteHotel;
import com.toth_almos.hotelreservationsystem.model.Hotel;
import com.toth_almos.hotelreservationsystem.repository.FavoriteHotelRepository;
import com.toth_almos.hotelreservationsystem.repository.HotelRepository;
import com.toth_almos.hotelreservationsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteHotelServiceImpl implements FavoriteHotelService {
    private final FavoriteHotelRepository favoriteHotelRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    public FavoriteHotelServiceImpl(FavoriteHotelRepository favoriteHotelRepository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.favoriteHotelRepository = favoriteHotelRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getFavorites(Long customerId) {
        return favoriteHotelRepository.findByCustomerId(customerId)
                .stream()
                .map(FavoriteHotel::getHotel)
                .toList();
    }

    @Override
    public void addFavorite(Long customerId, Long hotelId) {
        if(favoriteHotelRepository.findByCustomerIdAndHotelId(customerId, hotelId).isPresent()) {
            throw new IllegalStateException("This hotel already in favorite list!");
        }
        Customer customer = userRepository.findByCustomerId(customerId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel not found!"));

        FavoriteHotel favoriteHotel = new FavoriteHotel();
        favoriteHotel.setCustomer(customer);
        favoriteHotel.setHotel(hotel);

        favoriteHotelRepository.save(favoriteHotel);
    }

    @Override
    public void removeFavorite(Long customerId, Long hotelId) {
        FavoriteHotel fav = favoriteHotelRepository.findByCustomerIdAndHotelId(customerId, hotelId).orElseThrow(() -> new EntityNotFoundException("Favorite hotel not found!"));
        favoriteHotelRepository.delete(fav);
    }
}
