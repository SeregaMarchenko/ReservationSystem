package com.example.reservationsystem.model.dto;

import jakarta.persistence.Column;
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

    private String description;
}
