package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.custom_exception.CustomValidException;
import com.example.reservationsystem.model.User;
import com.example.reservationsystem.model.dto.create.UserCreateDto;
import com.example.reservationsystem.model.dto.update.user.UserUpdateAgeDto;
import com.example.reservationsystem.model.dto.update.user.UserUpdateUsernameDto;
import com.example.reservationsystem.security.service.UserSecurityService;
import com.example.reservationsystem.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;
    private final UserSecurityService userSecurityService;

    @Autowired
    public UserController(UserService userService, UserSecurityService userSecurityService) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        Optional<List<User>> result = userService.getAllUsers();
        return result.map(users -> new ResponseEntity<>(users, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> result = userService.getUserById(id);
        return result.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserCreateDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (userService.createUser(user)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/username")
    public ResponseEntity<HttpStatus> updateUserLastname(@RequestBody @Valid UserUpdateUsernameDto user) {
        if (userService.updateUsername(user)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/age")
    public ResponseEntity<HttpStatus> updateUserAge(@RequestBody @Valid UserUpdateAgeDto user) {
        if (userService.updateUserAge(user)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") Long id) {
        if (userService.deleteUserById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);//TODO yourself
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCurrentUser() {
        boolean isDeleted = userSecurityService.deleteCurrentUser();
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
