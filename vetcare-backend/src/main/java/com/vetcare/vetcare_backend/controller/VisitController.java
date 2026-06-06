package com.vetcare.vetcare_backend.controller;

import com.vetcare.vetcare_backend.entity.Visit;
import com.vetcare.vetcare_backend.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    // Log a new visit with prescriptions
    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestBody Visit visit) {
        Visit savedVisit = visitService.saveVisit(visit);
        return ResponseEntity.status(201).body(savedVisit);
    }

    // Get full medical history for a pet
    // URL: GET /api/visits/pet/1
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Visit>> getVisitsByPet(@PathVariable Long petId) {
        List<Visit> visits = visitService.getVisitsByPet(petId);
        return ResponseEntity.ok(visits);
    }

    // Get today's visits for a clinic
    // URL: GET /api/visits/clinic/1/today
    @GetMapping("/clinic/{clinicId}/today")
    public ResponseEntity<List<Visit>> getTodaysVisits(@PathVariable Long clinicId) {
        List<Visit> visits = visitService.getTodaysVisits(clinicId, LocalDate.now());
        return ResponseEntity.ok(visits);
    }

    // Get all visits for a clinic
    // URL: GET /api/visits/clinic/1
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<Visit>> getAllVisitsByClinic(@PathVariable Long clinicId) {
        List<Visit> visits = visitService.getAllVisitsByClinic(clinicId);
        return ResponseEntity.ok(visits);
    }
}