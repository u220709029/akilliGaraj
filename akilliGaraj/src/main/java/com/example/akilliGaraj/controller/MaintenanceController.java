package com.example.akilliGaraj.controller;



import com.example.akilliGaraj.dtos.request.MaintenanceCreateRequest;
import com.example.akilliGaraj.dtos.response.MaintenanceResponse;
import com.example.akilliGaraj.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenances")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponse> addMaintenance(@Valid @RequestBody MaintenanceCreateRequest request) {
        log.info("MaintenanceController: {} id'li araç için bakım kaydı ekleniyor.", request.getVehicleId());
        MaintenanceResponse response = maintenanceService.addMaintenanceRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<MaintenanceResponse>> getMaintenanceHistory(@PathVariable Long vehicleId) {
        log.info("MaintenanceController: {} id'li aracın bakım geçmişi listeleniyor.", vehicleId);
        List<MaintenanceResponse> history = maintenanceService.getVehicleMaintenanceHistory(vehicleId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MaintenanceResponse> getMaintenanceRecordById(@PathVariable Long recordId) {
        log.info("MaintenanceController: {} id'li bakım kaydı detayı isteniyor.", recordId);
        MaintenanceResponse response = maintenanceService.getMaintenanceRecordById(recordId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<MaintenanceResponse> updateMaintenance(
            @PathVariable Long recordId,
            @Valid @RequestBody MaintenanceCreateRequest request) {
        log.info("MaintenanceController: {} id'li bakım kaydı güncelleme isteği.", recordId);
        MaintenanceResponse response = maintenanceService.updateMaintenanceRecord(recordId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long recordId) {
        log.info("MaintenanceController: {} id'li bakım kaydı siliniyor.", recordId);
        maintenanceService.deleteMaintenanceRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MaintenanceResponse>> checkUpcomingMaintenances(
            @RequestParam(value = "days", defaultValue = "15") int daysAhead) {
        log.info("MaintenanceController: Önümüzdeki {} gün içindeki kritik bakımlar taranıyor.", daysAhead);
        List<MaintenanceResponse> responses = maintenanceService.checkUpcomingMaintenances(daysAhead);
        return ResponseEntity.ok(responses);
    }
}
