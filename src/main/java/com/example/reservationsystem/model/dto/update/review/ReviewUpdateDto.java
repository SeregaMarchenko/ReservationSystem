package com.example.reservationsystem.model.dto.update.review;

import com.example.reservationsystem.annotation.Rating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReviewUpdateDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 200)
    private String comment;

    @NotNull
    private Long event_id;

    @NotNull
    @Rating
    private int rating;

    @NotNull
    private Long user_id;
}
