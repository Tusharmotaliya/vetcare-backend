package com.vetcare.vetcare_backend.service;

import com.vetcare.vetcare_backend.entity.Medicine;
import com.vetcare.vetcare_backend.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    // Add a new medicine to inventory
    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    // Get all medicines for a clinic
    public List<Medicine> getAllMedicines(Long clinicId) {
        return medicineRepository.findByClinicId(clinicId);
    }

    // Get only low stock medicines — for dashboard alert
    public List<Medicine> getLowStockMedicines(Long clinicId) {
        return medicineRepository.findLowStockMedicines(clinicId);
    }

    // Update stock quantity — called when doctor adds new stock
    public Medicine updateStock(Long medicineId, Integer newQuantity) {
        Medicine medicine = medicineRepository
                .findById(medicineId)
                .orElseThrow(() -> new RuntimeException(
                        "Medicine not found with id: " + medicineId));

        medicine.setStockQuantity(newQuantity);
        return medicineRepository.save(medicine);
    }

    // Deduct stock when medicine is prescribed
    // Called automatically after a visit is saved
    public void deductStock(Long clinicId, String medicineName, int quantity) {
        medicineRepository
                .findByClinicIdAndName(clinicId, medicineName)
                .ifPresent(medicine -> {
                    int newStock = medicine.getStockQuantity() - quantity;
                    // Stock can't go below 0
                    medicine.setStockQuantity(Math.max(newStock, 0));
                    medicineRepository.save(medicine);
                });
    }
}