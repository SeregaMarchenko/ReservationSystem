package com.example.reservationsystem.model.dto;

import com.example.reservationsystem.annotation.FutureTimestamp;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class EventCreateDto {
    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @FutureTimestamp
    private Timestamp reservationDate;

    @NotNull
    @Size(min = 1, max = 30)
    private String location;

    private String description;

    @NotNull
    @Min(1)
    @Max(10000)
    private int capacity;
}
