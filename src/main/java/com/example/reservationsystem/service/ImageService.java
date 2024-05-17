package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Image;
import com.example.reservationsystem.model.dto.ImageCreateDto;
import com.example.reservationsystem.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<List<Image>> getAllImages() {
        return Optional.of(imageRepository.findAll());
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Boolean createImage(ImageCreateDto imageCreateDto) {
        Image image = new Image();
        image.setData(imageCreateDto.getData());
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
