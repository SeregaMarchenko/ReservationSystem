package com.example.reservationsystem.service;

import com.example.reservationsystem.exeption.custom_exception.FieldException;
import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.create.PlaceCreateDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateDescriptionDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateLocationDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateNameDto;
import com.example.reservationsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Optional<List<Place>> getAllPlaces() {
        return Optional.of(placeRepository.findAll());
    }

    public Optional<Place> getPlaceById(Long id) {
        return placeRepository.findById(id);
    }

    public Boolean createPlace(PlaceCreateDto placeFromDto) {
        Place place = new Place();
        place.setDescription(placeFromDto.getDescription());
        place.setLocation(placeFromDto.getLocation());
        place.setName(placeFromDto.getName());
        place.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        place.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        Place newPlace = placeRepository.save(place);
        return getPlaceById(newPlace.getId()).isPresent();
    }

    public Boolean updatePlace(Place place) {
        Optional<Place> placeFromDBOptional = placeRepository.findById(place.getId());
        if (placeFromDBOptional.isPresent()) {
            Place placeFromDB = placeFromDBOptional.get();
            if (place.getLocation() != null) {
                placeFromDB.setLocation(place.getLocation());
            }
            if (place.getName() != null) {
                placeFromDB.setName(place.getName());
            }
            placeFromDB.setDescription(place.getDescription());
            placeFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Place updatePlace = placeRepository.saveAndFlush(placeFromDB);
            return updatePlace.equals(placeFromDB);
        }
        return false;
    }

    public Boolean deletePlaceById(Long id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
            return !placeRepository.existsById(id);
        }
        return false;
    }

    public Boolean updatePlaceName(PlaceUpdateNameDto place) {
        Optional<Place> placeFromDBOptional = placeRepository.findById(place.getId());
        if (placeFromDBOptional.isPresent()) {
            Place placeFromDB = placeFromDBOptional.get();
            placeFromDB.setName(place.getName());
            placeFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Place updatePlace = placeRepository.saveAndFlush(placeFromDB);
            return updatePlace.equals(placeFromDB);
        }
        return false;
    }

    public Boolean updatePlaceDescription(PlaceUpdateDescriptionDto place) {
        Optional<Place> placeFromDBOptional = placeRepository.findById(place.getId());
        if (placeFromDBOptional.isPresent()) {
            Place placeFromDB = placeFromDBOptional.get();
            placeFromDB.setDescription(place.getDescription());
            placeFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Place updatePlace = placeRepository.saveAndFlush(placeFromDB);
            return updatePlace.equals(placeFromDB);
        }
        return false;
    }

    public Boolean updatePlaceLocation(PlaceUpdateLocationDto place) {
        Optional<Place> placeFromDBOptional = placeRepository.findById(place.getId());
        if (placeFromDBOptional.isPresent()) {
            Place placeFromDB = placeFromDBOptional.get();
            placeFromDB.setLocation(place.getLocation());
            placeFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Place updatePlace = placeRepository.saveAndFlush(placeFromDB);
            return updatePlace.equals(placeFromDB);
        }
        return false;
    }

    public Optional<List<Place>> getAllPlacesAndSortByField(String field) {
        if (field.equals("name") || field.equals("location") ||
                field.equals("description") || field.equals("created") || field.equals("changed")) {
            return Optional.of(placeRepository.findAll(Sort.by(field)));
        } else {
            throw new FieldException(field + " no such field");
        }
    }

    public Optional<List<Place>> searchPlacesByLocation(String location) {
        return Optional.of(placeRepository.findByLocationContainingIgnoreCase(location));
    }

    public Optional<List<Place>> searchPlacesByName(String name) {
        return Optional.of(placeRepository.findByNameContainingIgnoreCase(name));
    }

    public Optional<List<Place>> searchPlacesByDescription(String description) {
        return Optional.of(placeRepository.findByDescriptionContainingIgnoreCase(description));
    }
}
