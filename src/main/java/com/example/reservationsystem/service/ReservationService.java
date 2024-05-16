package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Reservation;
import com.example.reservationsystem.model.dto.ReservationCreateDto;
import com.example.reservationsystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Optional<List<Reservation>> getAllReservations() {
        return Optional.of(reservationRepository.findAll());
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Boolean createReservation(ReservationCreateDto reservationFromDto) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationFromDto.getReservationDate());
        reservation.setStatus(reservationFromDto.getStatus());
        Reservation newReservation = reservationRepository.save(reservation);
        return getReservationById(newReservation.getId()).isPresent();
    }

    public Boolean deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
        return getReservationById(id).isEmpty();
    }

    public Boolean updateReservation(Reservation reservation) {
        Optional<Reservation> reservationFromDBOptional = reservationRepository.findById(reservation.getId());
        if (reservationFromDBOptional.isPresent()) {
            Reservation reservationFromDB = reservationFromDBOptional.get();
            if (reservation.getReservationDate() != null) {
                reservationFromDB.setReservationDate(reservation.getReservationDate());
            }
            if (reservation.getStatus() != null) {
                reservationFromDB.setStatus(reservation.getStatus());
            }
            reservationFromDB.setEvent(reservation.getEvent());
            reservationFromDB.setUser(reservation.getUser());
            Reservation updateReservation = reservationRepository.saveAndFlush(reservationFromDB);
            return updateReservation.equals(reservationFromDB);
        }
        return false;
    }
}
