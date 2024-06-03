package com.example.reservationsystem.security.service;

import com.example.reservationsystem.security.model.UserSecurity;
import com.example.reservationsystem.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public CustomUserDetailService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserSecurity> securityInfoOptional = userSecurityRepository.findByUserUsername(username);
        if (securityInfoOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        UserSecurity securityInfo = securityInfoOptional.get();
        return User.builder()
                .username(securityInfo.getUsername())
                .password(securityInfo.getPassword())
                .roles(securityInfo.getRole().toString())
                .build();
    }
}