package com.auth_service.service;

import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.LoginResponse;
import com.auth_service.dto.SignUpRequest;
import com.auth_service.dto.JwtResponse;
import com.auth_service.entity.Role;
import com.auth_service.entity.User;
import com.auth_service.repository.AppUserRepository;
import com.auth_service.repository.UserRepository;
import com.auth_service.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       AppUserRepository appUserRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.appUserRepository = appUserRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("You are already registered");
        }

        Role role = appUserRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Not Found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(role);

        userRepository.save(user);
        return "User account created successfully";
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        String token = jwtService.generateToken(user.getUsername(), roles);
        return new LoginResponse(token);
    }

    public JwtResponse validate(String token) {
        boolean valid = jwtService.isTokenValid(token);
        if (!valid) {
            return new JwtResponse(null, List.of(), false);
        }
        return new JwtResponse(
                jwtService.extractUsername(token),
                jwtService.extractRoles(token),
                true
        );
    }
}

