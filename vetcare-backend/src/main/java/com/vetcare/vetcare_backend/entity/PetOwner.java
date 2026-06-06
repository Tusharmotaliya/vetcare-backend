package com.vetcare.vetcare_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pet_owners")
@Data
public class PetOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which clinic does this owner belong to? (multi-tenancy)
    @Column(name = "clinic_id", nullable = false)
    private Long clinicId;

    @Column(nullable = false)
    private String name;

    // Indian phone numbers are 10 digits — we store as String not Long
    // because numbers starting with 0 would lose the leading zero
    @Column(nullable = false, unique = true)
    private String phone;

    private String email;

    private String address;

    // This automatically saves the date+time when the owner was registered
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Before saving to DB, auto-set the createdAt timestamp
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}