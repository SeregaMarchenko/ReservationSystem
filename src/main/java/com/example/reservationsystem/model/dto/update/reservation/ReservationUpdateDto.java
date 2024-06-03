package com.example.reservationsystem.model.dto.update.reservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReservationUpdateDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(max = 300)
    private String comment;

    @NotNull
    private Long event_id;

    @NotNull
    private Long user_id;
}
