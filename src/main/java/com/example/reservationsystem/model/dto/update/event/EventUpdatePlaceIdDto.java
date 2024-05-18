package com.example.reservationsystem.model.dto.update.event;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventUpdatePlaceIdDto {
    @NotNull
    private Long id;

    @NotNull
    private Long place_id;
}
