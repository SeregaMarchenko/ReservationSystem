package com.example.reservationsystem.model.dto.create;

import com.example.reservationsystem.annotation.Adult;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserCreateDto {
    @NotNull
    @Size(min = 7, max = 20)
    private String username;

    @NotNull
    @Adult
    private Integer age;
}