package com.example.akilliGaraj.controller;



import com.example.akilliGaraj.dtos.request.FuelRecordCreateRequest;
import com.example.akilliGaraj.dtos.response.FuelRecordResponse;
import com.example.akilliGaraj.dtos.response.FuelSummaryResponse;
import com.example.akilliGaraj.service.FuelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fuels")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class FuelController {

    private final FuelService fuelService;

    @PostMapping
    public ResponseEntity<FuelRecordResponse> addFuelRecord(@Valid @RequestBody FuelRecordCreateRequest request) {
        log.info("FuelController: {} id'li araç için yakıt kaydı ekleme isteği.", request.getVehicleId());
        FuelRecordResponse response = fuelService.addFuelRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<FuelRecordResponse>> getVehicleFuelHistory(@PathVariable Long vehicleId) {
        log.info("FuelController: {} id'li aracın yakıt geçmişi isteniyor.", vehicleId);
        List<FuelRecordResponse> history = fuelService.getVehicleFuelHistory(vehicleId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<FuelRecordResponse> getFuelRecordById(@PathVariable Long recordId) {
        log.info("FuelController: {} id'li yakıt kaydı detayı isteniyor.", recordId);
        FuelRecordResponse response = fuelService.getFuelRecordById(recordId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/vehicle/{vehicleId}/summary")
    public ResponseEntity<FuelSummaryResponse> getVehicleFuelSummary(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(fuelService.getVehicleFuelSummary(vehicleId));
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<FuelRecordResponse> updateFuelRecord(
            @PathVariable Long recordId,
            @Valid @RequestBody FuelRecordCreateRequest request) {
        log.info("FuelController: {} id'li yakıt kaydı güncelleme isteği.", recordId);
        FuelRecordResponse response = fuelService.updateFuelRecord(recordId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteFuelRecord(@PathVariable Long recordId) {
        log.info("FuelController: {} id'li yakıt kaydı siliniyor.", recordId);
        fuelService.deleteFuelRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicle/{vehicleId}/average-consumption")
    public ResponseEntity<String> getAverageConsumption(@PathVariable Long vehicleId) {
        log.info("FuelController: {} id'li aracın ortalama tüketimi hesaplanıyor.", vehicleId);
        String result = fuelService.calculateAverageConsumption(vehicleId);
        return ResponseEntity.ok(result);
    }
}