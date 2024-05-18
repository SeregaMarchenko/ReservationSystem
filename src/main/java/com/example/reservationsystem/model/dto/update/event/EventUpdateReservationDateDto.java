package com.example.reservationsystem.model.dto.update.event;

import com.example.reservationsystem.annotation.FutureTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class EventUpdateReservationDateDto {
    @NotNull
    private Long id;

    @NotNull
    @FutureTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Timestamp reservationDate;
}
