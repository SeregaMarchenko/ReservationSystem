package com.example.reservationsystem.service;

import com.example.reservationsystem.exeption.IncorrectCapacityException;
import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.create.EventCreateDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateCapacityDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateDescriptionDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateLocationDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateNameDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdatePlaceIdDto;
import com.example.reservationsystem.model.dto.update.event.EventUpdateReservationDateDto;
import com.example.reservationsystem.repository.EventRepository;
import com.example.reservationsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Boolean updateEventName(EventUpdateNameDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            if (event.getName() != null) {
                eventFromDB.setName(event.getName());
            }
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventDescription(EventUpdateDescriptionDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            if (event.getDescription() != null) {
                eventFromDB.setDescription(event.getDescription());
            }
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventLocation(EventUpdateLocationDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            if (event.getLocation() != null) {
                eventFromDB.setLocation(event.getLocation());
            }
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
            }else {
                throw new IncorrectCapacityException("The number of reserves seats is more than the total number of seats indicated");
            }
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
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }

    public Boolean updateEventReservationDate(EventUpdateReservationDateDto event) {
        Optional<Event> eventFromDBOptional = eventRepository.findById(event.getId());
        if (eventFromDBOptional.isPresent()) {
            Event eventFromDB = eventFromDBOptional.get();
            if (event.getReservationDate() != null) {
                eventFromDB.setReservationDate(event.getReservationDate());
            }
            Event updateEvent = eventRepository.saveAndFlush(eventFromDB);
            return updateEvent.equals(eventFromDB);
        }
        return false;
    }


    public Optional<List<Event>> getAllEventsAndSortByField(String field) {
        return Optional.of(eventRepository.findAll(Sort.by(field)));
    }

    public Optional<List<Event>> getEventsWithPagination(int size, int page) {
        return Optional.of(eventRepository.findAll(PageRequest.ofSize(size).withPage(page)).getContent());
    }
}