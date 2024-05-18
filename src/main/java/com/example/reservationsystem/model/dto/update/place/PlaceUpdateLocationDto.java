package com.example.reservationsystem.model.dto.update.place;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlaceUpdateLocationDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 10, max = 30)
    private String location;
}
