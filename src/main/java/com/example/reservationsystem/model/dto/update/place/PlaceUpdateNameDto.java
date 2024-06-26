package com.example.reservationsystem.model.dto.update.place;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlaceUpdateNameDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;
}
