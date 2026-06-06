package com.vetcare.vetcare_backend.service;

import com.vetcare.vetcare_backend.entity.Pet;
import com.vetcare.vetcare_backend.entity.Prescription;
import com.vetcare.vetcare_backend.entity.Visit;
import com.vetcare.vetcare_backend.repository.PetRepository;
import com.vetcare.vetcare_backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PetRepository petRepository;

    public Visit saveVisit(Visit visit) {

        // Step 1: Load the full Pet object from DB using the pet id sent in request
        Long petId = visit.getPet().getId();
        Pet fullPet = petRepository
                .findById(petId)
                .orElseThrow(() -> new RuntimeException(
                        "Pet not found with id: " + petId));

        // Step 2: Attach the full pet to the visit
        visit.setPet(fullPet);

        // Step 3: Copy clinicId from the pet automatically
        visit.setClinicId(fullPet.getClinicId());

        // Step 4: Link each prescription back to this visit
        // This is needed because of the @OneToMany relationship —
        // each Prescription needs to know which Visit it belongs to
        if (visit.getPrescriptions() != null) {
            for (Prescription prescription : visit.getPrescriptions()) {
                prescription.setVisit(visit);
            }
        }

        // Step 5: Save the visit — Cascade saves all prescriptions automatically
        return visitRepository.save(visit);
    }

    // Get complete medical history for a pet
    public List<Visit> getVisitsByPet(Long petId) {
        return visitRepository.findByPetIdOrderByVisitDateDesc(petId);
    }

    // Get all visits for today at a clinic — used in dashboard
    public List<Visit> getTodaysVisits(Long clinicId, java.time.LocalDate date) {
        return visitRepository.findByClinicIdAndVisitDate(clinicId, date);
    }

    // Get all visits for a clinic
    public List<Visit> getAllVisitsByClinic(Long clinicId) {
        return visitRepository.findByClinicIdOrderByVisitDateDesc(clinicId);
    }
}