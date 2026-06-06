package com.vetcare.vetcare_backend.service;

import com.vetcare.vetcare_backend.dto.AuthResponse;
import com.vetcare.vetcare_backend.dto.LoginRequest;
import com.vetcare.vetcare_backend.dto.RegisterRequest;
import com.vetcare.vetcare_backend.entity.Clinic;
import com.vetcare.vetcare_backend.repository.ClinicRepository;
import com.vetcare.vetcare_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {

        // Check if email already exists
        if (clinicRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new clinic
        Clinic clinic = new Clinic();
        clinic.setName(request.getName());
        clinic.setEmail(request.getEmail());
        clinic.setPhone(request.getPhone());
        clinic.setAddress(request.getAddress());

        // NEVER store plain password — always encrypt it first
        // BCrypt turns "mypassword123" into "$2a$10$xyz..." (one-way hash)
        clinic.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        Clinic savedClinic = clinicRepository.save(clinic);

        // Generate token immediately after registration
        String token = jwtUtil.generateToken(
                savedClinic.getEmail(),
                savedClinic.getId()
        );

        return new AuthResponse(
                token,
                savedClinic.getId(),
                savedClinic.getName(),
                "Registration successful"
        );
    }

    public AuthResponse login(LoginRequest request) {

        // Find clinic by email
        Clinic clinic = clinicRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Compare entered password with stored hash
        // passwordEncoder.matches() does this securely
        if (!passwordEncoder.matches(request.getPassword(), clinic.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(clinic.getEmail(), clinic.getId());

        return new AuthResponse(
                token,
                clinic.getId(),
                clinic.getName(),
                "Login successful"
        );
    }
}