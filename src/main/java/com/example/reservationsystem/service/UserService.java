package com.example.reservationsystem.service;

import com.example.reservationsystem.model.User;
import com.example.reservationsystem.model.dto.create.UserCreateDto;
import com.example.reservationsystem.model.dto.update.user.UserUpdateAgeDto;
import com.example.reservationsystem.model.dto.update.user.UserUpdateDto;
import com.example.reservationsystem.model.dto.update.user.UserUpdateUsernameDto;
import com.example.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<List<User>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean createUser(UserCreateDto userFromDto) {
        User user = new User();
        user.setUsername(userFromDto.getUsername());
        user.setAge(userFromDto.getAge());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        User newUser = userRepository.save(user);
        return getUserById(newUser.getId()).isPresent();
    }

    public Boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return !userRepository.existsById(id);
        }
        return false;
    }

    public Boolean updateUser(UserUpdateDto user) {
        Optional<User> userFromDBOptional = userRepository.findById(user.getId());
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            userFromDB.setUsername(user.getUsername());
            userFromDB.setAge(user.getAge());
            userFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updateUser = userRepository.saveAndFlush(userFromDB);
            return updateUser.equals(userFromDB);
        }
        return false;
    }

    public Boolean updateUsername(UserUpdateUsernameDto user) {
        Optional<User> userFromDBOptional = userRepository.findById(user.getId());
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            userFromDB.setUsername(user.getUsername());
            userFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updateUser = userRepository.saveAndFlush(userFromDB);
            return updateUser.equals(userFromDB);
        }
        return false;
    }

    public Boolean updateUserAge(UserUpdateAgeDto user) {
        Optional<User> userFromDBOptional = userRepository.findById(user.getId());
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            userFromDB.setAge(user.getAge());
            userFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updateUser = userRepository.saveAndFlush(userFromDB);
            return updateUser.equals(userFromDB);
        }
        return false;
    }
}
