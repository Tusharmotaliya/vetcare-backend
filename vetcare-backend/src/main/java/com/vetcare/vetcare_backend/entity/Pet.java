package com.vetcare.vetcare_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinic_id", nullable = false)
    private Long clinicId;

    // This links each pet to their owner
    // @ManyToOne = many pets can belong to one owner
    // @JoinColumn = the foreign key column in the pets table
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private PetOwner owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species;

    private String breed;

    private LocalDate dob;

    private Double weight;

    @Column(name = "photo_url")
    private String photoUrl;
}