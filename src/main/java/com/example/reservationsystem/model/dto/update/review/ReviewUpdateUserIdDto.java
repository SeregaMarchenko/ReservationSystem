package com.example.reservationsystem.model.dto.update.review;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReviewUpdateUserIdDto {
    @NotNull
    private Long id;

    @NotNull
    private Long user_id;
}
