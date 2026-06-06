package com.vetcare.vetcare_backend.repository;

import com.vetcare.vetcare_backend.entity.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {

    // Find all owners belonging to a specific clinic
    List<PetOwner> findByClinicId(Long clinicId);

    // Search owner by phone number — useful when owner walks in
    // Optional means: this might return a result or might return empty
    Optional<PetOwner> findByPhone(String phone);
}