package com.example.reservationsystem.model.dto.update.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReservationUpdateEventIdDto {
    @NotNull
    private Long id;

    @NotNull
    private Long user_id;
}