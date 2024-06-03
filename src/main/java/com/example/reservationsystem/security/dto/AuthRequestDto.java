package com.example.reservationsystem.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthRequestDto {
    @NotNull
    @Size(min = 7, max = 20)
    private String username;

    @NotNull
    @Size(min = 10, max = 30)
    private String password;

}
