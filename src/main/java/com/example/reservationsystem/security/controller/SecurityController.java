package com.example.reservationsystem.security.controller;

import com.example.reservationsystem.exeption.custom_exception.CustomValidException;
import com.example.reservationsystem.security.dto.AuthRequestDto;
import com.example.reservationsystem.security.dto.AuthResponseDto;
import com.example.reservationsystem.security.dto.RegistrationDto;
import com.example.reservationsystem.security.dto.UserSecurityUpdateIsBlockDto;
import com.example.reservationsystem.security.service.UserSecurityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/security")
@SecurityRequirement(name = "Bearer Authentication")
public class SecurityController {

    private UserSecurityService userSecurityService;

    @Autowired
    public SecurityController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid RegistrationDto registrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        userSecurityService.registration(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody @Valid AuthRequestDto authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        Optional<String> token = userSecurityService.generateToken(authRequest);
        return token.map(s -> new ResponseEntity<>(new AuthResponseDto(s), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/is_block")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody @Valid UserSecurityUpdateIsBlockDto userSecurityUpdateIsBlockDto,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (userSecurityService.updateIsBlock(userSecurityUpdateIsBlockDto)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
