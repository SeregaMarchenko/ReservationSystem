package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.CustomValidException;
import com.example.reservationsystem.model.Review;
import com.example.reservationsystem.model.dto.ReviewCreateDto;
import com.example.reservationsystem.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        Optional<List<Review>> result = reviewService.getAllReviews();
        return result.map(reviews -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id) {
        Optional<Review> result = reviewService.getReviewById(id);
        return result.map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createReview(@RequestBody @Valid ReviewCreateDto review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (reviewService.createReview(review)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateReview(@RequestBody Review review) {
        if (reviewService.updateReview(review)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReviewById(@PathVariable("id") Long id) {
        if (reviewService.deleteReviewById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/sort/{field}")
    public ResponseEntity<List<Review>> getAllReviewsAndSortByField(@PathVariable("field") String field) {
        Optional<List<Review>> result = reviewService.getAllReviewsAndSortByField(field);
        return result.map(reviews -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sort/{field}/{page}")
    public ResponseEntity<List<Review>> getAllReviewsAndSortByField(@PathVariable("field") Integer field, @PathVariable("page") Integer page) {
        Optional<List<Review>> result = reviewService.getReviewsWithPagination(field, page);
        return result.map(reviews -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
