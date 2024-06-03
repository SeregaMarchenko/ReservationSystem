package com.example.reservationsystem.security.dto;

import com.example.reservationsystem.annotation.Boolean;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserSecurityUpdateIsBlockDto {
    @NotNull
    private Long id;
    @NotNull
    @Boolean
    private String is_block;
}
