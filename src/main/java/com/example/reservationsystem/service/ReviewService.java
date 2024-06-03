package com.example.reservationsystem.service;

import com.example.reservationsystem.model.Event;
import com.example.reservationsystem.model.Review;
import com.example.reservationsystem.model.User;
import com.example.reservationsystem.model.dto.create.ReviewCreateDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateCommentDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateEventIdDto;
import com.example.reservationsystem.model.dto.update.review.ReviewUpdateUserIdDto;
import com.example.reservationsystem.repository.EventRepository;
import com.example.reservationsystem.repository.ReviewRepository;
import com.example.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Optional<List<Review>> getAllReviews() {
        return Optional.of(reviewRepository.findAll());
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Boolean createReview(ReviewCreateDto reviewCreateDto) {
        Review review = new Review();
        review.setDate(Timestamp.valueOf(LocalDateTime.now()));
        review.setComment(reviewCreateDto.getComment());
        review.setRating(reviewCreateDto.getRating());
        Optional<Event> eventFromDB = eventRepository.findById(reviewCreateDto.getEvent_id());
        Optional<User> userFromDB = userRepository.findById(reviewCreateDto.getUser_id());
        if (eventFromDB.isPresent() && userFromDB.isPresent()) {
            review.setUser(userFromDB.get());
            review.setEvent(eventFromDB.get());
            review.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            review.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Review newReview = reviewRepository.save(review);
            return getReviewById(newReview.getId()).isPresent();
        } else {
            throw new NoSuchElementException("Event or user not found.");
        }
    }

    public Boolean deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
        return getReviewById(id).isEmpty();
    }

    public Boolean updateReview(ReviewUpdateDto review) {
        Optional<Review> reviewFromDBOptional = reviewRepository.findById(review.getId());
        if (reviewFromDBOptional.isPresent()) {
            Review reviewFromDB = reviewFromDBOptional.get();
            if (review.getRating() != 0) {
                reviewFromDB.setRating(review.getRating());
            }
            Optional<User> userFromDB = userRepository.findById(review.getUser_id());
            if (userFromDB.isPresent()) {
                reviewFromDB.setUser(userFromDB.get());
            } else {
                throw new NoSuchElementException("User not found.");
            }
            Optional<Event> eventFromDB = eventRepository.findById(review.getEvent_id());
            if (eventFromDB.isPresent()) {
                reviewFromDB.setEvent(eventFromDB.get());
            } else {
                throw new NoSuchElementException("Event not found.");
            }
            reviewFromDB.setComment(review.getComment());
            reviewFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Review updateReview = reviewRepository.saveAndFlush(reviewFromDB);
            return updateReview.equals(reviewFromDB);
        }
        return false;
    }

    public Boolean updateReviewComment(ReviewUpdateCommentDto review) {
        Optional<Review> reviewFromDBOptional = reviewRepository.findById(review.getId());
        if (reviewFromDBOptional.isPresent()) {
            Review reviewFromDB = reviewFromDBOptional.get();
            reviewFromDB.setComment(review.getComment());
            reviewFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Review updateReview = reviewRepository.saveAndFlush(reviewFromDB);
            return updateReview.equals(reviewFromDB);
        }
        return false;
    }

    public Boolean updateReviewUserId(ReviewUpdateUserIdDto review) {
        Optional<Review> reviewFromDBOptional = reviewRepository.findById(review.getId());
        if (reviewFromDBOptional.isPresent()) {
            Review reviewFromDB = reviewFromDBOptional.get();
            Optional<User> userFromDB = userRepository.findById(review.getUser_id());
            if (userFromDB.isPresent()) {
                reviewFromDB.setUser(userFromDB.get());
            } else {
                throw new NoSuchElementException("User not found.");
            }
            reviewFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Review updateReview = reviewRepository.saveAndFlush(reviewFromDB);
            return updateReview.equals(reviewFromDB);
        }
        return false;
    }

    public Boolean updateReviewEventId(ReviewUpdateEventIdDto review) {
        Optional<Review> reviewFromDBOptional = reviewRepository.findById(review.getId());
        if (reviewFromDBOptional.isPresent()) {
            Review reviewFromDB = reviewFromDBOptional.get();
            Optional<Event> eventFromDB = eventRepository.findById(review.getEvent_id());
            if (eventFromDB.isPresent()) {
                reviewFromDB.setEvent(eventFromDB.get());
            } else {
                throw new NoSuchElementException("Event not found.");
            }
            reviewFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            Review updateReview = reviewRepository.saveAndFlush(reviewFromDB);
            return updateReview.equals(reviewFromDB);
        }
        return false;
    }

    public Optional<List<Review>> getAllReviewsAndSortByField(String field) {
        return Optional.of(reviewRepository.findAll(Sort.by(field)));
    }

    public Optional<List<Review>> getReviewsWithPagination(int size, int page) {
        return Optional.of(reviewRepository.findAll(PageRequest.ofSize(size).withPage(page)).getContent());
    }
}
