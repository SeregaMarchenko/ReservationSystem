package com.example.reservationsystem.model.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ImageCreateDto {
    private Long place_id;

    private Long event_id;
}