package com.example.reservationsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class ReservationCreateDto {
    @NotNull
    private Long user_id;

    @NotNull
    private Long event_id;
}