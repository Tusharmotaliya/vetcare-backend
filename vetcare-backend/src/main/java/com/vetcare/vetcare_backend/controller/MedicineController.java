package com.vetcare.vetcare_backend.controller;

import com.vetcare.vetcare_backend.entity.Medicine;
import com.vetcare.vetcare_backend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // Add a new medicine to inventory
    @PostMapping
    public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine medicine) {
        Medicine saved = medicineService.addMedicine(medicine);
        return ResponseEntity.status(201).body(saved);
    }

    // Get all medicines for a clinic
    // URL: GET /api/medicines/clinic/1
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<Medicine>> getAllMedicines(@PathVariable Long clinicId) {
        List<Medicine> medicines = medicineService.getAllMedicines(clinicId);
        return ResponseEntity.ok(medicines);
    }

    // Get low stock medicines — for dashboard alert
    // URL: GET /api/medicines/clinic/1/low-stock
    @GetMapping("/clinic/{clinicId}/low-stock")
    public ResponseEntity<List<Medicine>> getLowStock(@PathVariable Long clinicId) {
        List<Medicine> lowStock = medicineService.getLowStockMedicines(clinicId);
        return ResponseEntity.ok(lowStock);
    }

    // Update stock quantity when new stock arrives
    // URL: PUT /api/medicines/1/stock?quantity=100
    @PutMapping("/{medicineId}/stock")
    public ResponseEntity<Medicine> updateStock(
            @PathVariable Long medicineId,
            @RequestParam Integer quantity) {
        Medicine updated = medicineService.updateStock(medicineId, quantity);
        return ResponseEntity.ok(updated);
    }
}