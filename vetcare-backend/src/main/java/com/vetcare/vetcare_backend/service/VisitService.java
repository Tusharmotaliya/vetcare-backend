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

    @Autowired
    private MedicineService medicineService;

    // This connects the WhatsApp notification service
    @Autowired
    private NotificationService notificationService;

    public Visit saveVisit(Visit visit) {

        // Step 1: Load full pet from DB
        Long petId = visit.getPet().getId();
        Pet fullPet = petRepository
                .findById(petId)
                .orElseThrow(() -> new RuntimeException(
                        "Pet not found with id: " + petId));

        // Step 2: Attach full pet and copy clinicId
        visit.setPet(fullPet);
        visit.setClinicId(fullPet.getClinicId());

        // Step 3: Link each prescription back to this visit
        if (visit.getPrescriptions() != null) {
            for (Prescription prescription : visit.getPrescriptions()) {
                prescription.setVisit(visit);
            }
        }

        // Step 4: Save the visit
        Visit savedVisit = visitRepository.save(visit);

        // Step 5: Auto-deduct medicine stock
        if (savedVisit.getPrescriptions() != null) {
            for (Prescription prescription : savedVisit.getPrescriptions()) {
                medicineService.deductStock(
                        savedVisit.getClinicId(),
                        prescription.getMedicineName(),
                        1
                );
            }
        }

        // Step 6: Send WhatsApp to pet owner
        // Runs AFTER save — if WhatsApp fails, visit is still saved safely
        notificationService.sendVisitSummaryWhatsApp(savedVisit);

        return savedVisit;
    }

    public List<Visit> getVisitsByPet(Long petId) {
        return visitRepository.findByPetIdOrderByVisitDateDesc(petId);
    }

    public List<Visit> getTodaysVisits(Long clinicId, java.time.LocalDate date) {
        return visitRepository.findByClinicIdAndVisitDate(clinicId, date);
    }

    public List<Visit> getAllVisitsByClinic(Long clinicId) {
        return visitRepository.findByClinicIdOrderByVisitDateDesc(clinicId);
    }
}