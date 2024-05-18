package com.example.reservationsystem.model.dto.update.user;

import com.example.reservationsystem.annotation.Adult;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserUpdateAgeDto {
    @NotNull
    private Long id;

    @NotNull
    @Adult
    private Integer age;
}
