package com.vetcare.vetcare_backend.repository;

import com.vetcare.vetcare_backend.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    // Get full medical history for a specific pet
    // Becomes: SELECT * FROM visits WHERE pet_id = ? ORDER BY visit_date DESC
    List<Visit> findByPetIdOrderByVisitDateDesc(Long petId);

    // Get all visits for a clinic on a specific date (for daily schedule view)
    List<Visit> findByClinicIdAndVisitDate(Long clinicId, LocalDate visitDate);

    // Get all visits for a clinic (for dashboard)
    List<Visit> findByClinicIdOrderByVisitDateDesc(Long clinicId);
}