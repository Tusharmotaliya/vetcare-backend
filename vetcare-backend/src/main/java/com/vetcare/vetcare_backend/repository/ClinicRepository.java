package com.vetcare.vetcare_backend.repository;

import com.vetcare.vetcare_backend.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    // Find clinic by email — used during login
    Optional<Clinic> findByEmail(String email);

    // Check if email already registered — used during signup
    boolean existsByEmail(String email);
}