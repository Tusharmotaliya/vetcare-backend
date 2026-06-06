package com.vetcare.vetcare_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "clinics")
@Data
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;             // "Sharma Pet Clinic"

    @Column(nullable = false, unique = true)
    private String email;            // login email

    // We never store plain passwords — always store the encrypted version
    // The actual encryption happens in the service layer
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String phone;

    private String address;

    @Column(name = "subscription_plan")
    private String subscriptionPlan; // "starter" or "growth"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.subscriptionPlan == null) {
            this.subscriptionPlan = "starter";
        }
    }
}