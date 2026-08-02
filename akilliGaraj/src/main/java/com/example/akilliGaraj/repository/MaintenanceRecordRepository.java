package com.example.akilliGaraj.repository;


import com.example.akilliGaraj.entities.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRecordRepository
        extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord>
    findAllByVehicleIdOrderByMaintenanceDateDesc(Long vehicleId);

    @Query("""
           SELECT m
           FROM MaintenanceRecord m
           WHERE m.maintenanceDate <= :targetDate
           ORDER BY m.maintenanceDate ASC
           """)
    List<MaintenanceRecord> findUpcomingMaintenances(
            @Param("targetDate") LocalDate targetDate);

    List<MaintenanceRecord> findByVehicleIdOrderByMaintenanceDateDesc(Long vehicleId);
}