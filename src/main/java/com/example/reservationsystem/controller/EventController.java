package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.CustomValidException;
import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.dto.EventCreateDto;
import com.example.reservationsystem.service.EventService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;


    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        Optional<List<Event>> result = eventService.getAllEvents();
        return result.map(events -> new ResponseEntity<>(events, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Optional<Event> result = eventService.getEventById(id);
        return result.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEvent(@RequestBody @Valid EventCreateDto event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (eventService.createEvent(event)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateEvent(@RequestBody Event event) {
        if (eventService.updateEvent(event)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEventById(@PathVariable("id") Long id) {
        if (eventService.deleteEventById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/sort/{field}")
    public ResponseEntity<List<Event>> getAllEventsAndSortByField(@PathVariable("field") String field) {
        Optional<List<Event>> result = eventService.getAllEventsAndSortByField(field);
        return result.map(events -> new ResponseEntity<>(events, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sort/{field}/{page}")
    public ResponseEntity<List<Event>> getAllEventsAndSortByField(@PathVariable("field") Integer field, @PathVariable("page") Integer page) {
        Optional<List<Event>> result = eventService.getEventsWithPagination(field, page);
        return result.map(events -> new ResponseEntity<>(events, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
