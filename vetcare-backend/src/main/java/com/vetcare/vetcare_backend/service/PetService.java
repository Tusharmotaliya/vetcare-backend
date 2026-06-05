package com.vetcare.vetcare_backend.service;

import com.vetcare.vetcare_backend.entity.Pet;
import com.vetcare.vetcare_backend.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet) {
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

