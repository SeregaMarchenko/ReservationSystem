package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.create.PlaceCreateDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateDescriptionDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateLocationDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateNameDto;
import com.example.reservationsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public Boolean deletePlaceById(Long id) {
        placeRepository.deleteById(id);
        return getPlaceById(id).isEmpty();
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
            placeFromDB.setName(place.getLocation());
            placeFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Place updatePlace = placeRepository.saveAndFlush(placeFromDB);
            return updatePlace.equals(placeFromDB);
        }
        return false;
    }

    public Optional<List<Place>> getAllPlacesAndSortByField(String field) {
        return Optional.of(placeRepository.findAll(Sort.by(field)));
    }

    public Optional<List<Place>> getPlacesWithPagination(int size, int page) {
        return Optional.of(placeRepository.findAll(PageRequest.ofSize(size).withPage(page)).getContent());
    }
}
