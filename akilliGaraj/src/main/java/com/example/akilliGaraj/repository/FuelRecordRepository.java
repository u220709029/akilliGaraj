package com.example.akilliGaraj.repository;

import org.springframework.data.jpa.repository.JpaRepository;



import com.example.akilliGaraj.entities.FuelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {


    List<FuelRecord> findByVehicleIdAndIsFullTankTrueOrderByOdometerAtFillDesc(Long vehicleId, Pageable pageable);


    @Query("SELECT SUM(f.totalCost) FROM FuelRecord f WHERE f.vehicle.id = :vehicleId")
    Double calculateTotalFuelCostByVehicleId(@Param("vehicleId") Long vehicleId);
    List<FuelRecord> findByVehicleIdOrderByOdometerAtFillDesc(Long vehicleId);
}