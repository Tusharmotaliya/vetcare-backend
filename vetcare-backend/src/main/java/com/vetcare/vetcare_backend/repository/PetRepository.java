package com.vetcare.vetcare_backend.repository;

import com.vetcare.vetcare_backend.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByClinicId(Long clinicId);
}

//Why is this an interface and not a class?
//Because Spring writes the actual code for you behind the scenes. You just declare what you need, Spring figures out how to do it. By extending JpaRepository<Pet, Long> you automatically get save(), findAll(), findById(), delete() — without writing a single line of SQL.
//The method findByClinicId — Spring reads the name and generates: SELECT * FROM pets WHERE clinic_id = ?. That's the magic of Spring Data JPA.