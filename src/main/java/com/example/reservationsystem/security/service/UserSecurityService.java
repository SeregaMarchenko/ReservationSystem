package com.example.reservationsystem.security.service;

import com.example.reservationsystem.exeption.custom_exception.AccessException;
import com.example.reservationsystem.exeption.custom_exception.SameUserInDatabase;
import com.example.reservationsystem.model.User;
import com.example.reservationsystem.repository.ReservationRepository;
import com.example.reservationsystem.repository.ReviewRepository;
import com.example.reservationsystem.repository.UserRepository;
import com.example.reservationsystem.security.dto.AuthRequestDto;
import com.example.reservationsystem.security.dto.RegistrationDto;
import com.example.reservationsystem.security.dto.UserSecurityUpdateIsBlockDto;
import com.example.reservationsystem.security.model.Roles;
import com.example.reservationsystem.security.model.UserSecurity;
import com.example.reservationsystem.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSecurityService {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserSecurityRepository userSecurityRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public UserSecurityService(JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                               UserSecurityRepository userSecurityRepository,
                               UserRepository userRepository, ReviewRepository reviewRepository, ReservationRepository reservationRepository) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDto registrationDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByUserUsername(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setAge(registrationDto.getAge());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        User savedUser = userRepository.save(user);
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUsername(registrationDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(registrationDto.getUserPassword()));
        userSecurity.setRole(Roles.USER);
        userSecurity.setUserId(savedUser.getId());
        userSecurity.setIsBlocked(false);
        userSecurityRepository.save(userSecurity);
    }

    public Optional<String> generateToken(AuthRequestDto authRequestDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByUserUsername(authRequestDto.getUsername());
        if (security.isPresent()
                && passwordEncoder.matches(authRequestDto.getPassword(), security.get().getPassword())) {
            if(security.get().getIsBlocked().equals(false)) {
                return Optional.of(jwtUtils.generateJwtToken(authRequestDto.getUsername()));
            }else {
                throw new AccessException("User is blocked");
            }
        }
        return Optional.empty();
    }

    public boolean deleteCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<UserSecurity> userOptional = userSecurityRepository.findByUsername(currentUsername);
        if (userOptional.isPresent()) {
            userSecurityRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }

    public Boolean updateIsBlock(UserSecurityUpdateIsBlockDto userSecurityUpdateIsBlockDto) {
        Optional<UserSecurity> userSecurityDBOptional = userSecurityRepository.findById(userSecurityUpdateIsBlockDto.getId());
        if (userSecurityDBOptional.isPresent()) {
            UserSecurity userSecurityFromDB = userSecurityDBOptional.get();
            userSecurityFromDB.setIsBlocked( Boolean.valueOf(userSecurityUpdateIsBlockDto.getIs_block()));
            UserSecurity updateUserSecurity = userSecurityRepository.saveAndFlush(userSecurityFromDB);
            return updateUserSecurity.equals(userSecurityFromDB);
        }
        return false;
    }
}
