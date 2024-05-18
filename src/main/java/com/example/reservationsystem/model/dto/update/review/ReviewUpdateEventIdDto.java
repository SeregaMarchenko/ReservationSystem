package com.example.reservationsystem.model.dto.update.review;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReviewUpdateEventIdDto {
    @NotNull
    private Long id;

    @NotNull
    private Long event_id;
}
