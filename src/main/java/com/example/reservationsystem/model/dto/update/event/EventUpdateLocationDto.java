package com.example.reservationsystem.model.dto.update.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventUpdateLocationDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String location;
}
