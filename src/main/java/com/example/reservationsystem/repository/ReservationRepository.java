package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
