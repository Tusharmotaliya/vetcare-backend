package com.vetcare.vetcare_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prescriptions")
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each prescription belongs to one visit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    @JsonBackReference
    private Visit visit;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;     // "Paracetamol"

    private String dosage;           // "500mg"

    private String duration;         // "5 days"

    private String instructions;     // "Give after food, twice a day"
}