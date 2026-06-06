package com.vetcare.vetcare_backend.service;

import com.vetcare.vetcare_backend.entity.Pet;
import com.vetcare.vetcare_backend.entity.PetOwner;
import com.vetcare.vetcare_backend.repository.PetOwnerRepository;
import com.vetcare.vetcare_backend.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public Pet savePet(Pet pet) {
        // Step 1: Get the owner's ID from the request
        Long ownerId = pet.getOwner().getId();

        // Step 2: Load the FULL owner object from database using that ID
        PetOwner fullOwner = petOwnerRepository
                .findById(ownerId)
                .orElseThrow(() -> new RuntimeException(
                        "Owner not found with id: " + ownerId));

        // Step 3: Attach the full owner to the pet
        pet.setOwner(fullOwner);

        // Step 4: Copy clinicId from owner — no need to send it in the request
        pet.setClinicId(fullOwner.getClinicId());

        // Step 5: Save the pet
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByClinic(Long clinicId) {
        return petRepository.findByClinicId(clinicId);
    }
}


//What is @Autowired?
//Normally in Java you'd write PetRepository petRepository = new PetRepository() to create an object. In Spring, you never use new for your own classes. Instead, @Autowired tells Spring: "you manage this object and give it to me when I need it." This is called Dependency Injection — one of the most important concepts in Spring.

