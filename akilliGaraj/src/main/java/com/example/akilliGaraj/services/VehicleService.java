package com.example.akilliGaraj.services;




import com.example.akilliGaraj.dtos.request.VehicleCreateRequest;
import com.example.akilliGaraj.dtos.response.VehicleResponse;
import java.util.List;

public interface VehicleService {
    // CREATE
    VehicleResponse addVehicle(Long userId, VehicleCreateRequest request);

    // READ
    List<VehicleResponse> getUserVehicles(Long userId);
    VehicleResponse getVehicleById(Long vehicleId);

    // UPDATE (Eksik olan)
    VehicleResponse updateVehicle(Long vehicleId, VehicleCreateRequest request);

    // DELETE
    void deleteVehicle(Long vehicleId);
}