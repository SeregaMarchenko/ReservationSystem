package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.custom_exception.CustomValidException;
import com.example.reservationsystem.model.Reservation;
import com.example.reservationsystem.model.dto.create.ReservationCreateDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateCommentDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateEventIdDto;
import com.example.reservationsystem.model.dto.update.reservation.ReservationUpdateUserIdDto;
import com.example.reservationsystem.security.service.UserSecurityService;
import com.example.reservationsystem.service.ReservationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/reservation")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {
    private final ReservationService reservationService;
    private final UserSecurityService userSecurityService;

    @Autowired
    public ReservationController(ReservationService reservationService, UserSecurityService userSecurityService) {
        this.reservationService = reservationService;
        this.userSecurityService = userSecurityService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        Optional<List<Reservation>> result = reservationService.getAllReservations();
        return result.map(reservations -> new ResponseEntity<>(reservations, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Optional<Reservation> result = reservationService.getReservationById(id);
        return result.map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteReservationByIdCurrentUser(@RequestParam("id") Long id) {
        if (userSecurityService.deleteReservationByIdCurrentUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }*/

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<HttpStatus> createReservation(@RequestBody @Valid ReservationCreateDto reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (reservationService.createReservation(reservation)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping
    public ResponseEntity<HttpStatus> updateReservation(@RequestBody ReservationUpdateDto reservation) {
        if (reservationService.updateReservation(reservation)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user_id")
    public ResponseEntity<HttpStatus> updateReservationUserId(@RequestBody @Valid ReservationUpdateUserIdDto reservation) {
        if (reservationService.updateReservationUserId(reservation)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/comment")
    public ResponseEntity<HttpStatus> updateReservationComment(@RequestBody @Valid ReservationUpdateCommentDto reservation) {
        if (reservationService.updateReservationComment(reservation)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/event_id")
    public ResponseEntity<HttpStatus> updateReservationEventId(@RequestBody @Valid ReservationUpdateEventIdDto reservation) {
        if (reservationService.updateReservationEventId(reservation)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReservationById(@PathVariable("id") Long id) {
        if (reservationService.deleteReservationById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
