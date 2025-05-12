package com.toth_almos.hotelreservationsystem.repository;

import com.toth_almos.hotelreservationsystem.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @EntityGraph(attributePaths = {"rooms"})
    @Override
    Page<Hotel> findAll(Specification<Hotel> spec, Pageable pageable);

    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.rooms WHERE h.id = :id AND h.deleted = false")
    Optional<Hotel> findById(@Param("id") Long id);

    public List<Hotel> findByCountryAndDeletedFalse(String country);
    public List<Hotel> findByNameContainingIgnoreCaseAndDeletedFalse(String name);
}
