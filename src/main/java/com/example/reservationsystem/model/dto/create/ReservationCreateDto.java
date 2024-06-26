package com.example.reservationsystem.model.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class ReservationCreateDto {
    @NotNull
    private Long user_id;

    @NotNull
    private Long event_id;

    @Size(max = 300)
    private String comment;
}