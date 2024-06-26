package com.example.reservationsystem.service;

import com.example.reservationsystem.exeption.custom_exception.FieldException;
import com.example.reservationsystem.exeption.custom_exception.IncorrectCapacityException;
import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.create.EventCreateDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateCapacityDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateDescriptionDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateLocationDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateNameDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdatePlaceIdDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateReservationDateDto;
import com.example.reservationsystem.repository.EventRepository;
import com.example.reservationsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
            event.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            event.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event newEvent = eventRepository.save(event);
            return getEventById(newEvent.getId()).isPresent();
        } else {
            throw new NoSuchElementException("Place not found.");
        }
    }

    public Boolean deleteEventById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return !eventRepository.existsById(id);
        }
        return false;
    }

    public Boolean updateEvent(EventUpdateDto event) {
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
            Optional<Place> placeFromDb = placeRepository.findById(event.getPlace_id());
            if (placeFromDb.isPresent()) {
                eventFromDB.setPlace(placeFromDb.get());
            } else {
                throw new NoSuchElementException("Place not found.");
            }
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventName(EventUpdateNameDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            eventFromDB.setName(event.getName());
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventDescription(EventUpdateDescriptionDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            eventFromDB.setDescription(event.getDescription());
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventLocation(EventUpdateLocationDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            eventFromDB.setLocation(event.getLocation());
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventCapacity(EventUpdateCapacityDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            if (event.getCapacity() >= eventFromDB.getCapacity()) {
                eventFromDB.setCapacity(event.getCapacity());
            } else {
                throw new IncorrectCapacityException("The number of reserves seats is more than the total number of seats indicated");
            }
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventPlaceId(EventUpdatePlaceIdDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            Optional<Place> placeFromDB = placeRepository.findById(event.getPlace_id());
            if (placeFromDB.isPresent()) {
                eventFromDB.setPlace(placeFromDB.get());
            } else {
                throw new NoSuchElementException("Place not found.");
            }
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventReservationDate(EventUpdateReservationDateDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            eventFromDB.setReservationDate(event.getReservationDate());
            eventFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }


    public Optional<List<Event>> getAllEventsAndSortByField(String field) {
        if (field.equals("name") || field.equals("reservationDate") || field.equals("location") ||
                field.equals("description") || field.equals("capacity") || field.equals("placeId") ||
                field.equals("created") || field.equals("changed")) {
            return Optional.of(eventRepository.findAll(Sort.by(field)));
        } else {
            throw new FieldException(field + " no such field");
        }
    }

    public Optional<List<Event>> searchEventsByLocation(String location) {
        return Optional.of(eventRepository.findByLocationContainingIgnoreCase(location));
    }

    public Optional<List<Event>> searchEventsByName(String name) {
        return Optional.of(eventRepository.findByNameContainingIgnoreCase(name));
    }

    public Optional<List<Event>> searchEventsByDescription(String description) {
        return Optional.of(eventRepository.findByDescriptionContainingIgnoreCase(description));
    }

    public Optional<List<Event>> searchEventsByCapacity(Integer capacity) {
        return Optional.of(eventRepository.findByCapacity(capacity));
    }
}