package com.vetcare.vetcare_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity //tells Spring "this class = a database table"
@Table(name = "pets") //the table will be named pets in PostgreSQL
@Data //Lombok auto-generates all getters/setters so you don't have to write them
public class Pet {

    @Id // this field is the primary key, auto-numbered 1, 2, 3...
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false) — this field is required, can't be empty
    @Column(name = "clinic_id", nullable = false)
    private Long clinicId;

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