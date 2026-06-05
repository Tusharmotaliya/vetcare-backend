package com.vetcare.vetcare_backend.controller;

import com.vetcare.vetcare_backend.entity.Pet;
import com.vetcare.vetcare_backend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        Pet savedPet = petService.savePet(pet);
        return ResponseEntity.status(201).body(savedPet);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }
}