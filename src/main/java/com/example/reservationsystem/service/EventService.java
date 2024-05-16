package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.dto.EventCreateDto;
import com.example.reservationsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<List<Event>> getAllEvents() {
        return Optional.of(eventRepository.findAll());
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Boolean createEvent(EventCreateDto eventCreateDto) {
        Event event = new Event();
        event.setDescription(eventCreateDto.getDescription());
        event.setCapacity(eventCreateDto.getCapacity());
        event.setLocation(event.getLocation());
        event.setName(eventCreateDto.getName());
        event.setReservationDate(eventCreateDto.getReservationDate());
        Event newEvent = eventRepository.save(event);
        return getEventById(newEvent.getId()).isPresent();
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
            eventFromDB.setReviews(event.getReviews());
            eventFromDB.setReservations(event.getReservations());
            eventFromDB.setImages(event.getImages());
            eventFromDB.setPlace(event.getPlace());
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }
}