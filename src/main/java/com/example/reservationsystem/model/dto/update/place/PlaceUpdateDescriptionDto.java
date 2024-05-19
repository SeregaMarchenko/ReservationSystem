package com.example.reservationsystem.model.dto.update.place;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlaceUpdateDescriptionDto {
    @NotNull
    private Long id;

    @Size(max = 200)
    private String description;
}
//TODO Status, when user update change chTimetamp, add fiela createTi and changeTi