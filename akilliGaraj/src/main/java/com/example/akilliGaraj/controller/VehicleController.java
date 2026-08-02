package com.example.akilliGaraj.controller;

import com.example.akilliGaraj.dtos.request.VehicleCreateRequest;
import com.example.akilliGaraj.dtos.response.VehicleResponse;
import com.example.akilliGaraj.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;


    @PostMapping("/user/{userId}")
    public ResponseEntity<VehicleResponse> addVehicle(
            @PathVariable Long userId,
            @Valid @RequestBody VehicleCreateRequest request) {
        log.info("VehicleController: {} id'li kullanıcı için araç ekleme isteği alındı.", userId);
        VehicleResponse response = vehicleService.addVehicle(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VehicleResponse>> getUserVehicles(@PathVariable Long userId) {
        log.info("VehicleController: {} id'li kullanıcının araçları listeleniyor.", userId);
        List<VehicleResponse> responses = vehicleService.getUserVehicles(userId);
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long vehicleId) {
        log.info("VehicleController: {} id'li araç detayları isteniyor.", vehicleId);
        VehicleResponse response = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable Long vehicleId,
            @Valid @RequestBody VehicleCreateRequest request) {
        log.info("VehicleController: {} id'li araç güncelleme isteği alındı.", vehicleId);
        VehicleResponse response = vehicleService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
        log.info("VehicleController: {} id'li araç siliniyor.", vehicleId);
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.noContent().build();
    }
}