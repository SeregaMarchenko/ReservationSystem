package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
