package com.example.reservationsystem.model.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlaceCreateDto {
    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @Size(min = 10, max = 30)
    private String location;

    @Size(max = 200)
    private String description;
}
