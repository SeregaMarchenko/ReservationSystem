package com.example.reservationsystem.model.dto.update.review;

import com.example.reservationsystem.annotation.Rating;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReviewUpdateRatingDto {
    @NotNull
    private Long id;

    @NotNull
    @Rating
    private int rating;
}
