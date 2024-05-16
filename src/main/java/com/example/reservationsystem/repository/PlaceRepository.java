package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,Long> {
}
