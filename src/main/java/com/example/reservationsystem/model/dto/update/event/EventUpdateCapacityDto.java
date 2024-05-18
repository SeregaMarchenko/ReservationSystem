package com.example.reservationsystem.model.dto.update.event;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventUpdateCapacityDto {
    @NotNull
    private Long id;

    @NotNull
    @Min(1)
    @Max(10000)
    private int capacity;
}
