package com.example.reservationsystem.service;

import com.example.reservationsystem.exeption.custom_exception.FullCapacityException;
import com.example.reservationsystem.exeption.custom_exception.UpdateException;
import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Reservation;
import com.example.reservationsystem.model.User;
import com.example.reservationsystem.model.dto.create.ReservationCreateDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateCommentDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateEventIdDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateUserIdDto;
import com.example.reservationsystem.repository.EventRepository;
import com.example.reservationsystem.repository.ReservationRepository;
import com.example.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Optional<List<Reservation>> getAllReservations() {
        return Optional.of(reservationRepository.findAll());
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Transactional(rollbackFor = {FullCapacityException.class, NoSuchElementException.class, UpdateException.class})
    public Boolean createReservation(ReservationCreateDto reservationFromDto) {
        Reservation reservation = new Reservation();
        Optional<Event> eventFromDB = eventRepository.findById(reservationFromDto.getEvent_id());
        Optional<User> userFromDB = userRepository.findById(reservationFromDto.getUser_id());
        if (eventFromDB.isPresent() && userFromDB.isPresent()) {
            Event event = eventFromDB.get();
            if (event.getCapacity() > 0) {
                event.setCapacity(event.getCapacity() - 1);
            } else {
                throw new FullCapacityException("No reservation available");
            }
            if (!(eventRepository.saveAndFlush(event).equals(event))) {
                throw new UpdateException("Update Event failed.");
            }
            reservation.setComment(reservationFromDto.getComment());
            reservation.setUser(userFromDB.get());
            reservation.setEvent(event);
            reservation.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            reservation.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Reservation newReservation = reservationRepository.save(reservation);
            return getReservationById(newReservation.getId()).isPresent();
        } else {
            throw new NoSuchElementException("Event or user not found.");
        }
    }

    public Boolean deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
        return getReservationById(id).isEmpty();
    }

    public Boolean updateReservation(ReservationUpdateDto reservation) {
        Optional<Reservation> reservationFromDBOptional = reservationRepository.findById(reservation.getId());
        if (reservationFromDBOptional.isPresent()) {
            Reservation reservationFromDB = reservationFromDBOptional.get();
            reservationFromDB.setComment(reservation.getComment());
            Optional<User> userFromDB = userRepository.findById(reservation.getUser_id());
            if (userFromDB.isPresent()) {
                reservationFromDB.setUser(userFromDB.get());
            } else {
                throw new NoSuchElementException("User not found.");
            }
            Optional<Event> eventFromDB = eventRepository.findById(reservation.getEvent_id());
            if (eventFromDB.isPresent()) {
                reservationFromDB.setEvent(eventFromDB.get());
            } else {
                throw new NoSuchElementException("Event not found.");
            }
            reservationFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Reservation updateReservation = reservationRepository.saveAndFlush(reservationFromDB);
            return updateReservation.equals(reservationFromDB);
        }
        return false;
    }

    public Boolean updateReservationUserId(ReservationUpdateUserIdDto reservation) {
        Optional<Reservation> reservationFromDBOptional = reservationRepository.findById(reservation.getId());
        if (reservationFromDBOptional.isPresent()) {
            Reservation reservationFromDB = reservationFromDBOptional.get();
            Optional<User> userFromDB = userRepository.findById(reservation.getUser_id());
            if (userFromDB.isPresent()) {
                reservationFromDB.setUser(userFromDB.get());
            } else {
                throw new NoSuchElementException("User not found.");
            }
            reservationFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Reservation updateReservation = reservationRepository.saveAndFlush(reservationFromDB);
            return updateReservation.equals(reservationFromDB);
        }
        return false;
    }

    public Boolean updateReservationEventId(ReservationUpdateEventIdDto reservation) {
        Optional<Reservation> reservationFromDBOptional = reservationRepository.findById(reservation.getId());
        if (reservationFromDBOptional.isPresent()) {
            Reservation reservationFromDB = reservationFromDBOptional.get();
            Optional<Event> eventFromDB = eventRepository.findById(reservation.getEvent_id());
            if (eventFromDB.isPresent()) {
                reservationFromDB.setEvent(eventFromDB.get());
            } else {
                throw new NoSuchElementException("Event not found.");
            }
            reservationFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Reservation updateReservation = reservationRepository.saveAndFlush(reservationFromDB);
            return updateReservation.equals(reservationFromDB);
        }
        return false;
    }

    public Boolean updateReservationComment(ReservationUpdateCommentDto reservation) {
        Optional<Reservation> reservationFromDBOptional = reservationRepository.findById(reservation.getId());
        if (reservationFromDBOptional.isPresent()) {
            Reservation reservationFromDB = reservationFromDBOptional.get();
            reservationFromDB.setComment(reservation.getComment());
            reservationFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Reservation updateReservation = reservationRepository.saveAndFlush(reservationFromDB);
            return updateReservation.equals(reservationFromDB);
        }
        return false;
    }
}
