package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.custom_exception.CustomValidException;
import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.create.PlaceCreateDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateDescriptionDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateLocationDto;
import com.example.reservationsystem.model.dto.update.place.PlaceUpdateNameDto;
import com.example.reservationsystem.service.PlaceService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/place")
@SecurityRequirement(name = "Bearer Authentication")
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<Place>> getAllPlaces() {
        Optional<List<Place>> result = placeService.getAllPlaces();
        return result.map(places -> new ResponseEntity<>(places, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<Place>> searchPlacesByLocation(@PathVariable("location") String location) {
        Optional<List<Place>> result = placeService.searchPlacesByLocation(location);
        return result.map(places -> new ResponseEntity<>(places, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/search/description/{description}")
    public ResponseEntity<List<Place>> searchPlacesByDescription(@PathVariable("description") String description) {
        Optional<List<Place>> result = placeService.searchPlacesByDescription(description);
        return result.map(places -> new ResponseEntity<>(places, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Place>> searchPlacesByName(@PathVariable("name") String name) {
        Optional<List<Place>> result = placeService.searchPlacesByName(name);
        return result.map(places -> new ResponseEntity<>(places, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable("id") Long id) {
        Optional<Place> result = placeService.getPlaceById(id);
        return result.map(place -> new ResponseEntity<>(place, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createPlace(@RequestBody @Valid PlaceCreateDto place, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (placeService.createPlace(place)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<HttpStatus> updatePlace(@RequestBody Place place) {
        if (placeService.updatePlace(place)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/name")
    public ResponseEntity<HttpStatus> updatePlaceName(@RequestBody @Valid PlaceUpdateNameDto place) {
        if (placeService.updatePlaceName(place)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/location")
    public ResponseEntity<HttpStatus> updatePlaceLocation(@RequestBody @Valid PlaceUpdateLocationDto place) {
        if (placeService.updatePlaceLocation(place)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/description")
    public ResponseEntity<HttpStatus> updatePlaceDescription(@RequestBody @Valid PlaceUpdateDescriptionDto place) {
        if (placeService.updatePlaceDescription(place)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePlaceById(@PathVariable("id") Long id) {
        if (placeService.deletePlaceById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<Place>> getAllPlacesAndSortByField(@PathVariable("field") String field) {
        Optional<List<Place>> result = placeService.getAllPlacesAndSortByField(field);
        return result.map(places -> new ResponseEntity<>(places, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}