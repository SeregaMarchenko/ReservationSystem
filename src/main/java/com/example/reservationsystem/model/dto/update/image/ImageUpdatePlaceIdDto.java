package com.example.reservationsystem.model.dto.update.image;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ImageUpdatePlaceIdDto {
    @NotNull
    private Long id;

    @NotNull
    private Long place_id;
}
