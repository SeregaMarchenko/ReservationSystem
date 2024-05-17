package com.example.reservationsystem.model.dto;

import com.example.reservationsystem.annotation.Rating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class ReviewCreateDto {
    @NotNull
    @Rating
    private int rating;

    @Size(max = 200)
    private String comment;

    @NotNull
    private Timestamp date;
}
