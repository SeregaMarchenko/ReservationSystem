package com.example.reservationsystem.model.dto.update.place;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlaceUpdateDescriptionDto {
    @NotNull
    private Long id;

    @NotNull
    private String description;
}
