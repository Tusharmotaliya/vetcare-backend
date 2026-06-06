package com.vetcare.vetcare_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicines")
@Data
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinic_id", nullable = false)
    private Long clinicId;

    @Column(nullable = false)
    private String name;             // "Paracetamol Vet"

    // Unit tells us what we're counting
    // Examples: "strips", "bottles", "vials", "tablets", "ml"
    private String unit;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;   // Current stock: 50

    // When stock falls to or below this number, show low stock alert
    @Column(name = "reorder_level", nullable = false)
    private Integer reorderLevel;    // Alert threshold: 10

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}