package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.custom_exception.CustomValidException;
import com.example.reservationsystem.model.Review;
import com.example.reservationsystem.model.dto.create.ReviewCreateDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateCommentDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateEventIdDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateRatingDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateUserIdDto;
import com.example.reservationsystem.security.service.UserSecurityService;
import com.example.reservationsystem.service.ReviewService;
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
@RequestMapping("/review")
@SecurityRequirement(name = "Bearer Authentication")
public class ReviewController {
    private final ReviewService reviewService;

    private final UserSecurityService userSecurityService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserSecurityService userSecurityService) {
        this.reviewService = reviewService;
        this.userSecurityService = userSecurityService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        Optional<List<Review>> result = reviewService.getAllReviews();
        return result.map(reviews -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id) {
        Optional<Review> result = reviewService.getReviewById(id);
        return result.map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
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

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping
    public ResponseEntity<HttpStatus> updateReview(@RequestBody ReviewUpdateDto review) {
        if (reviewService.updateReview(review)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/comment")
    public ResponseEntity<HttpStatus> updateReviewComment(@RequestBody @Valid ReviewUpdateCommentDto review) {
        if (reviewService.updateReviewComment(review)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/user_id")
    public ResponseEntity<HttpStatus> updateReviewUserId(@RequestBody @Valid ReviewUpdateUserIdDto review) {
        if (reviewService.updateReviewUserId(review)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/event_id")
    public ResponseEntity<HttpStatus> updateReviewEventId(@RequestBody @Valid ReviewUpdateEventIdDto review) {
        if (reviewService.updateReviewEventId(review)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/rating")
    public ResponseEntity<HttpStatus> updateReviewRating(@RequestBody @Valid ReviewUpdateRatingDto review) {
        if (reviewService.updateReviewRating(review)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReviewById(@PathVariable("id") Long id) {
        if (reviewService.deleteReviewById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<Review>> getAllReviewsAndSortByField(@PathVariable("field") String field) {
        Optional<List<Review>> result = reviewService.getAllReviewsAndSortByField(field);
        return result.map(reviews -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/sort/{size}/{page}")
    public ResponseEntity<List<Review>> getAllReviewsAndSortByField(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        Optional<List<Review>> result = reviewService.getReviewsWithPagination(size, page);
        return result.map(reviews -> new ResponseEntity<>(reviews, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
