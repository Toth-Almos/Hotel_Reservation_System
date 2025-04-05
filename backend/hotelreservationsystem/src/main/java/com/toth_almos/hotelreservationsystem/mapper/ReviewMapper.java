package com.toth_almos.hotelreservationsystem.mapper;

import com.toth_almos.hotelreservationsystem.dto.ReviewDTO;
import com.toth_almos.hotelreservationsystem.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "hotel.name", target = "hotelName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.username", target = "customerName")
    ReviewDTO toDTO(Review review);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Review toEntity(ReviewDTO reviewDTO);

    List<ReviewDTO> toDTOList(List<Review> reviews);
    List<Review> toEntityList(List<ReviewDTO> reviewDTOs);
}
