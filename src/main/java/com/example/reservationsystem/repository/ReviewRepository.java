package com.example.reservationsystem.repository;

import com.example.reservationsystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
