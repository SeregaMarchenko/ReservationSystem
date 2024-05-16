package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
