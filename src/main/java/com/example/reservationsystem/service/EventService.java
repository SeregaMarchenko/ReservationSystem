package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.EventCreateDto;
import com.example.reservationsystem.repository.EventRepository;
import com.example.reservationsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    private final PlaceRepository placeRepository;

    @Autowired
    public EventService(EventRepository eventRepository, PlaceRepository placeRepository) {
        this.eventRepository = eventRepository;
        this.placeRepository = placeRepository;
    }

    public Optional<List<Event>> getAllEvents() {
        return Optional.of(eventRepository.findAll());
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional(rollbackFor = NoSuchElementException.class)
    public Boolean createEvent(EventCreateDto eventCreateDto) {
        Event event = new Event();
        event.setDescription(eventCreateDto.getDescription());
        event.setCapacity(eventCreateDto.getCapacity());
        event.setLocation(eventCreateDto.getLocation());
        event.setName(eventCreateDto.getName());
        event.setReservationDate(eventCreateDto.getReservationDate());
        Optional<Place> placeFromDB = placeRepository.findById(eventCreateDto.getPlace_id());
        if (placeFromDB.isPresent()) {
            event.setPlace(placeFromDB.get());
            Event newEvent = eventRepository.save(event);
            return getEventById(newEvent.getId()).isPresent();
        } else {
            throw new NoSuchElementException("Place not found.");
        }
    }

    public Boolean deleteEventById(Long id) {
        eventRepository.deleteById(id);
        return getEventById(id).isEmpty();
    }

    public Boolean updateEvent(Event event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            if (event.getLocation() != null) {
                eventFromDB.setLocation(event.getLocation());
            }
            if (event.getCapacity() != 0) {
                eventFromDB.setCapacity(event.getCapacity());
            }
            if (event.getName() != null) {
                eventFromDB.setName(event.getName());
            }
            if (event.getReservationDate() != null) {
                eventFromDB.setReservationDate(event.getReservationDate());
            }
            eventFromDB.setDescription(event.getDescription());
            eventFromDB.setPlace(event.getPlace());
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }
}