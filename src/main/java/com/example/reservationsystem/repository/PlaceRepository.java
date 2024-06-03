package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByNameContainingIgnoreCase(String name);

    List<Place> findByLocationContainingIgnoreCase(String location);

    List<Place> findByDescriptionContainingIgnoreCase(String description);
}
