package com.vetcare.vetcare_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "visits")
@Data
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinic_id", nullable = false)
    private Long clinicId;

    // Many visits can belong to one pet
    // e.g. Bruno visited the clinic 5 times — 5 visit records all linked to Bruno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pet pet;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    // What the owner said when they came in: "dog is not eating, has fever"
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    // What the doctor concluded: "Viral fever, dehydration"
    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    // Any extra notes the doctor wants to record
    @Column(columnDefinition = "TEXT")
    private String notes;

    // When should this pet come back?
    @Column(name = "next_visit_date")
    private LocalDate nextVisitDate;

    // Timestamp of when this record was created
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        // Auto-set visit date to today if not provided
        if (this.visitDate == null) {
            this.visitDate = LocalDate.now();
        }
    }

    // One visit has many prescriptions
    // CascadeType.ALL means: when we save a Visit, automatically save its Prescriptions too
    // This is why we can send visit + medicines in ONE API call
    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Prescription> prescriptions;
}