package com.example.reservationsystem.model.dto.create;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ImageCreateDto {
    private Long place_id;

    private Long event_id;
}