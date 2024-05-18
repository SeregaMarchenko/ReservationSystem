package com.example.reservationsystem.model.dto.create;

import com.example.reservationsystem.annotation.Rating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReviewCreateDto {
    @NotNull
    @Rating
    private int rating;

    @Size(max = 200)
    private String comment;

    @NotNull
    private Long user_id;

    @NotNull
    private Long event_id;
}
