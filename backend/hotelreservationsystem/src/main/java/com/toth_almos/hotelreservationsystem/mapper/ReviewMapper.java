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
    @Mapping(source = "customer.id", target = "customerId")
    ReviewDTO toDTO(Review review);

    @Mapping(source = "hotelId", target = "hotel.id")
    @Mapping(source = "customerId", target = "customer.id")
    Review toEntity(ReviewDTO reviewDTO);

    List<ReviewDTO> toDTOList(List<Review> reviews);
    List<Review> toEntityList(List<ReviewDTO> reviewDTOs);
}
