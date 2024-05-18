package com.example.reservationsystem.model.dto.update.event;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventUpdateDescriptionDto {
    @NotNull
    private Long id;

    @NotNull
    private String description;
}
