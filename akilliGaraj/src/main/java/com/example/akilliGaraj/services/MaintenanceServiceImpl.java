package com.example.akilliGaraj.services;

import com.example.akilliGaraj.dtos.request.MaintenanceCreateRequest;
import com.example.akilliGaraj.dtos.response.MaintenanceResponse;
import com.example.akilliGaraj.entities.MaintenanceRecord;
import com.example.akilliGaraj.entities.Vehicle;
import com.example.akilliGaraj.mapper.MaintenanceMapper;
import com.example.akilliGaraj.repository.MaintenanceRecordRepository;
import com.example.akilliGaraj.repository.VehicleRepository;
import com.example.akilliGaraj.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    // Senin kendi repo ismin
    private final MaintenanceRecordRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceResponse addMaintenanceRecord(MaintenanceCreateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Araç bulunamadı. ID: " + request.getVehicleId()));

        // Kilometre kontrolü iş kuralı (YENİ EKLEDİK)
        if (request.getOdometerAtMaintenance() < vehicle.getCurrentOdometer()) {
            throw new RuntimeException("Girilen bakım kilometresi, aracın güncel kilometresinden düşük olamaz!");
        }

        MaintenanceRecord record = maintenanceMapper.toEntity(request);
        record.setVehicle(vehicle);

        // Aracın güncel kilometresini ileri yönlü güncelle (YENİ EKLEDİK)
        vehicle.setCurrentOdometer(request.getOdometerAtMaintenance());
        vehicleRepository.save(vehicle);

        MaintenanceRecord savedRecord = maintenanceRepository.save(record);
        log.info("Yeni bakım kaydı oluşturuldu. RecordId={}", savedRecord.getId());

        return maintenanceMapper.toResponse(savedRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponse> getVehicleMaintenanceHistory(Long vehicleId) {
        // Senin repodaki gerçek metot isminle çağırıyoruz
        return maintenanceRepository
                .findAllByVehicleIdOrderByMaintenanceDateDesc(vehicleId)
                .stream()
                .map(maintenanceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponse getMaintenanceRecordById(Long recordId) {
        MaintenanceRecord record = maintenanceRepository.findById(recordId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bakım kaydı bulunamadı. ID: " + recordId));

        return maintenanceMapper.toResponse(record);
    }

    @Override
    public MaintenanceResponse updateMaintenanceRecord(Long recordId, MaintenanceCreateRequest request) {
        MaintenanceRecord record = maintenanceRepository.findById(recordId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bakım kaydı bulunamadı. ID: " + recordId));

        record.setDescription(request.getDescription());
        record.setCost(request.getCost());
        record.setMaintenanceDate(request.getMaintenanceDate());
        record.setOdometerAtMaintenance(request.getOdometerAtMaintenance());

        MaintenanceRecord updatedRecord = maintenanceRepository.save(record);
        log.info("Bakım kaydı güncellendi. RecordId={}", recordId);

        return maintenanceMapper.toResponse(updatedRecord);
    }

    @Override
    public void deleteMaintenanceRecord(Long recordId) {
        if (!maintenanceRepository.existsById(recordId)) {
            throw new IllegalArgumentException(
                    "Silinecek bakım kaydı bulunamadı. ID: " + recordId);
        }

        maintenanceRepository.deleteById(recordId);
        log.info("Bakım kaydı silindi. RecordId={}", recordId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponse> checkUpcomingMaintenances(int daysAhead) {
        LocalDate targetDate = LocalDate.now().plusDays(daysAhead);

        return maintenanceRepository.findUpcomingMaintenances(targetDate)
                .stream()
                .map(maintenanceMapper::toResponse)
                .toList();
    }
}