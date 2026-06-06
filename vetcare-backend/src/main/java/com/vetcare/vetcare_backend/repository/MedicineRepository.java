package com.vetcare.vetcare_backend.repository;

import com.vetcare.vetcare_backend.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Get all medicines for a clinic
    List<Medicine> findByClinicId(Long clinicId);

    // Find medicine by name in a clinic (to avoid duplicates)
    Optional<Medicine> findByClinicIdAndName(Long clinicId, String name);

    // Get low stock medicines — where current stock is at or below reorder level
    // @Query lets us write custom JPQL (Java version of SQL) when
    // the method name approach isn't enough
    // m.stockQuantity <= m.reorderLevel means "stock is dangerously low"
    @Query("SELECT m FROM Medicine m WHERE m.clinicId = :clinicId " +
           "AND m.stockQuantity <= m.reorderLevel " +
           "ORDER BY m.stockQuantity ASC")
    List<Medicine> findLowStockMedicines(@Param("clinicId") Long clinicId);
}