package com.example.akilliGaraj.service;

import com.example.akilliGaraj.dtos.request.MaintenanceCreateRequest;
import com.example.akilliGaraj.dtos.response.MaintenanceResponse;
import java.util.List;

public interface MaintenanceService {
    // CREATE
    MaintenanceResponse addMaintenanceRecord(MaintenanceCreateRequest request);

    // READ
    List<MaintenanceResponse> getVehicleMaintenanceHistory(Long vehicleId);
    MaintenanceResponse getMaintenanceRecordById(Long recordId);

    // UPDATE
    MaintenanceResponse updateMaintenanceRecord(Long recordId, MaintenanceCreateRequest request);

    // DELETE
    void deleteMaintenanceRecord(Long recordId);

    // ÖZEL: Yaklaşan bakımları sorgula
    List<MaintenanceResponse> checkUpcomingMaintenances(int daysAhead);

}