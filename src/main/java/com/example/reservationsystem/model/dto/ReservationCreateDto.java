package com.example.reservationsystem.model.dto;

import com.example.reservationsystem.annotation.FutureTimestamp;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class ReservationCreateDto {
    @NotNull
    @FutureTimestamp
    private Timestamp reservationDate;
    @NotNull
    private Boolean status;
}