package com.example.reservationsystem.model.dto.update.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserUpdateLastnameDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 7, max = 20)
    private String lastname;
}
