package com.example.reservationsystem.service;

import com.example.reservationsystem.model.User;
import com.example.reservationsystem.model.dto.UserCreateDto;
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

    public Optional<List<User>> getAllUsers(){
        return Optional.of(userRepository.findAll());
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean createUser(UserCreateDto userFromDto) {
        User user = new User();
        user.setLastname(userFromDto.getLastname());
        user.setFirstname(userFromDto.getFirstname());
        user.setAge(userFromDto.getAge());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        User newUser = userRepository.save(user);
        return getUserById(newUser.getId()).isPresent();
    }

    public Boolean deleteUserById(Long id) {
        userRepository.deleteById(id);
        return getUserById(id).isEmpty();
    }

    public Boolean updateUser(User user) {
        Optional<User> userFromDBOptional = userRepository.findById(user.getId());
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            if (user.getFirstname() != null) {
                userFromDB.setFirstname(user.getFirstname());
            }
            if (user.getLastname() != null) {
                userFromDB.setLastname(user.getLastname());
            }
            if (user.getAge() != null) {
                userFromDB.setAge(user.getAge());
            }
            userFromDB.setUserSecurity(user.getUserSecurity());
            userFromDB.setReviews(user.getReviews());
            userFromDB.setReservations(user.getReservations());
            userFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updateUser = userRepository.saveAndFlush(userFromDB);
            return updateUser.equals(userFromDB);
        }
        return false;
    }
}
