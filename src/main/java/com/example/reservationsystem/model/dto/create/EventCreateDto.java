package com.example.reservationsystem.model.dto.create;

import com.example.reservationsystem.annotation.FutureTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Timestamp reservationDate;

    @NotNull
    @Size(min = 1, max = 30)
    private String location;

    @Size(max = 200)
    private String description;

    @NotNull
    @Min(1)
    @Max(10000)
    private int capacity;

    @NotNull
    private Long place_id;
}
