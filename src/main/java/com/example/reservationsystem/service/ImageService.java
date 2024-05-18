package com.example.reservationsystem.service;

import com.example.reservationsystem.exeption.EmptyFileException;
import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Image;
import com.example.reservationsystem.model.Place;
import com.example.reservationsystem.model.dto.ImageCreateDto;
import com.example.reservationsystem.repository.EventRepository;
import com.example.reservationsystem.repository.ImageRepository;
import com.example.reservationsystem.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final PlaceRepository placeRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, PlaceRepository placeRepository, EventRepository eventRepository) {
        this.imageRepository = imageRepository;
        this.placeRepository = placeRepository;
        this.eventRepository = eventRepository;
    }

    public Optional<List<Image>> getAllImages() {
        return Optional.of(imageRepository.findAll());
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Boolean createImage(ImageCreateDto imageCreateDto, MultipartFile file) {
        Image image = new Image();
        if (!file.isEmpty()) {
            try {
                image.setData(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new EmptyFileException("Image file is empty");
        }
        Optional<Place> placeFromDB = placeRepository.findById(imageCreateDto.getPlace_id());
        Optional<Event> eventFromDB = eventRepository.findById(imageCreateDto.getEvent_id());
        if (!(placeFromDB.isPresent() || eventFromDB.isPresent())) {
            throw new NoSuchElementException("There is not at least one existing Place and Event.");
        }
        image.setPlace(placeFromDB.get());
        image.setEvent(eventFromDB.get());
        Image newImage = imageRepository.save(image);
        return getImageById(newImage.getId()).isPresent();
    }

    public Boolean deleteImageById(Long id) {
        imageRepository.deleteById(id);
        return getImageById(id).isEmpty();
    }

    public Boolean updateImage(Image image) {
        Optional<Image> imageFromDBOptional = imageRepository.findById(image.getId());
        if (imageFromDBOptional.isPresent()) {
            Image imageFromDB = imageFromDBOptional.get();
            if (image.getData() != null) {
                imageFromDB.setData(image.getData());
            }
            imageFromDB.setEvent(image.getEvent());
            imageFromDB.setPlace(image.getPlace());
            Image updateImage = imageRepository.saveAndFlush(imageFromDB);
            return updateImage.equals(imageFromDB);
        }
        return false;
    }
}
