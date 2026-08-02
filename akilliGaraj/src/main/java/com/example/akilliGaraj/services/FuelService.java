package com.example.akilliGaraj.service;

import com.example.akilliGaraj.dtos.request.FuelRecordCreateRequest;
import com.example.akilliGaraj.dtos.response.FuelRecordResponse;
import com.example.akilliGaraj.dtos.response.FuelSummaryResponse;

import java.util.List;

public interface FuelService {
    // CREATE
    FuelRecordResponse addFuelRecord(FuelRecordCreateRequest request);

    // READ
    List<FuelRecordResponse> getVehicleFuelHistory(Long vehicleId);
    FuelRecordResponse getFuelRecordById(Long recordId); // Yeni

    // UPDATE
    FuelRecordResponse updateFuelRecord(Long recordId, FuelRecordCreateRequest request); // Yeni

    // DELETE
    void deleteFuelRecord(Long recordId); // Yeni

    // ÖZEL ALGORİTMA
    String calculateAverageConsumption(Long vehicleId);


    // Mevcut metotlarının altına ekle:
    FuelSummaryResponse getVehicleFuelSummary(Long vehicleId);
}