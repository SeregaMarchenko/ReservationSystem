package com.example.reservationsystem.controller;

import com.example.reservationsystem.exeption.CustomValidException;
import com.example.reservationsystem.model.User;
import com.example.reservationsystem.model.dto.UserCreateDto;
import com.example.reservationsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        Optional<List<User>> result = userService.getAllUsers();
        return result.map(users -> new ResponseEntity<>(users, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> result = userService.getUserById(id);
        return result.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") Long id) {
        if (userService.deleteUserById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
