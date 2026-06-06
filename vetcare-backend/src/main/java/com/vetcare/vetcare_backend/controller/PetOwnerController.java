package com.vetcare.vetcare_backend.controller;

import com.vetcare.vetcare_backend.entity.PetOwner;
import com.vetcare.vetcare_backend.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    // Register a new pet owner
    @PostMapping
    public ResponseEntity<PetOwner> createOwner(@RequestBody PetOwner owner) {
        PetOwner savedOwner = petOwnerService.saveOwner(owner);
        return ResponseEntity.status(201).body(savedOwner);
    }

    // Get all owners
    @GetMapping
    public ResponseEntity<List<PetOwner>> getAllOwners() {
        List<PetOwner> owners = petOwnerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    // Search owner by phone number
    // Example URL: GET /api/owners/search?phone=9876543210
    @GetMapping("/search")
    public ResponseEntity<?> searchByPhone(@RequestParam String phone) {
        return petOwnerService.findByPhone(phone)
                .map(owner -> ResponseEntity.ok(owner))
                .orElse(ResponseEntity.notFound().build());
    }
}