package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Review;
import com.example.reservationsystem.model.dto.ReviewCreateDto;
import com.example.reservationsystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Optional<List<Review>> getAllReviews() {
        return Optional.of(reviewRepository.findAll());
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Boolean createReview(ReviewCreateDto reviewCreateDto) {
        Review review = new Review();
        review.setDate(reviewCreateDto.getDate());
        review.setComment(reviewCreateDto.getComment());
        review.setRating(reviewCreateDto.getRating());
        Review newReview = reviewRepository.save(review);
        return getReviewById(newReview.getId()).isPresent();
    }

    public Boolean deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
        return getReviewById(id).isEmpty();
    }

    public Boolean updateReview(Review review) {
        Optional<Review> reviewFromDBOptional = reviewRepository.findById(review.getId());
        if (reviewFromDBOptional.isPresent()) {
            Review reviewFromDB = reviewFromDBOptional.get();
            if (review.getDate() != null) {
                reviewFromDB.setDate(review.getDate());
            }
            if (review.getRating() != 0) {
                reviewFromDB.setRating(review.getRating());
            }
            reviewFromDB.setEvent(review.getEvent());
            reviewFromDB.setUser(review.getUser());
            reviewFromDB.setComment(review.getComment());
            Review updateReview = reviewRepository.saveAndFlush(reviewFromDB);
            return updateReview.equals(reviewFromDB);
        }
        return false;
    }
}
