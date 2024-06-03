package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByNameContainingIgnoreCase(String name);

    List<Event> findByLocationContainingIgnoreCase(String location);

    List<Event> findByDescriptionContainingIgnoreCase(String description);

    List<Event> findByCapacity(Integer capacity);
}
