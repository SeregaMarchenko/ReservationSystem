package com.example.reservationsystem.security.dto;

import com.example.reservationsystem.annotation.Adult;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RegistrationDto {
    @NotNull
    @Size(min = 7, max = 20)
    private String login;

    @NotNull
    @Size(min = 10, max = 30)
    private String userPassword;

    @NotNull
    @Size(min = 7, max = 20)
    private String username;

    @NotNull
    @Adult
    private Integer age;

}
