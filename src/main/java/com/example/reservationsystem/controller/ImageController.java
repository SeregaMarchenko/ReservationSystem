package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.CustomValidException;
import com.example.reservationsystem.model.Image;
import com.example.reservationsystem.model.dto.create.ImageCreateDto;
import com.example.reservationsystem.model.dto.update.image.ImageUpdateEventIdDto;
import com.example.reservationsystem.model.dto.update.image.ImageUpdatePlaceIdDto;
import com.example.reservationsystem.service.ImageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        Optional<List<Image>> result = imageService.getAllImages();
        return result.map(images -> new ResponseEntity<>(images, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) {
        Optional<Image> result = imageService.getImageById(id);
        return result.map(image -> new ResponseEntity<>(image, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> createImage(@RequestBody ImageCreateDto image,
                                                  @RequestParam("file") MultipartFile file) {
        if (imageService.createImage(image, file)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateImage(@RequestBody Image image) {
        if (imageService.updateImage(image)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/event_id")
    public ResponseEntity<HttpStatus> updateImageEventId(@RequestBody @Valid ImageUpdateEventIdDto image) {
        if (imageService.updateImageEventId(image)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/place_id")
    public ResponseEntity<HttpStatus> updateImagePlaceId(@RequestBody @Valid ImageUpdatePlaceIdDto image) {
        if (imageService.updateImagePlaceId(image)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteImageById(@PathVariable("id") Long id) {
        if (imageService.deleteImageById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
