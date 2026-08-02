package com.example.akilliGaraj.repository;



import com.example.akilliGaraj.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    boolean existsByLicensePlate(String licensePlate);


    List<Vehicle> findAllByUserId(Long userId);
}