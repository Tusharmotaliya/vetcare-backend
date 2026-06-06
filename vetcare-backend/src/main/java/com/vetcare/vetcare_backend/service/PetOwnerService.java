package com.vetcare.vetcare_backend.service;

import com.vetcare.vetcare_backend.entity.PetOwner;
import com.vetcare.vetcare_backend.repository.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    public PetOwner saveOwner(PetOwner owner) {
        return petOwnerRepository.save(owner);
    }

    public List<PetOwner> getAllOwners() {
        return petOwnerRepository.findAll();
    }

    public List<PetOwner> getOwnersByClinic(Long clinicId) {
        return petOwnerRepository.findByClinicId(clinicId);
    }

    // Search by phone — doctor types phone number to find returning customer
    public Optional<PetOwner> findByPhone(String phone) {
        return petOwnerRepository.findByPhone(phone);
    }
}