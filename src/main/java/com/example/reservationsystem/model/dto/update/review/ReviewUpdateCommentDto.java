package com.example.reservationsystem.model.dto.update.review;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReviewUpdateCommentDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 200)
    private String comment;
}