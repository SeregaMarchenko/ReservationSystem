package com.example.reservationsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ImageCreateDto {
    @NotNull
    private byte[] data;
}