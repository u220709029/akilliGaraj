package com.example.akilliGaraj.services;

import com.example.akilliGaraj.dtos.request.VehicleCreateRequest;
import com.example.akilliGaraj.dtos.response.VehicleResponse;
import com.example.akilliGaraj.entities.User;
import com.example.akilliGaraj.entities.Vehicle;
import com.example.akilliGaraj.mapper.VehicleMapper;
import com.example.akilliGaraj.repository.UserRepository;
import com.example.akilliGaraj.repository.VehicleRepository;
import com.example.akilliGaraj.services.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@PreAuthorize
//@PostAuthorize()
@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    @Transactional
    public VehicleResponse addVehicle(Long userId, VehicleCreateRequest request) {
        log.info("Yeni araç ekleme isteği. Plaka: {}", request.getLicensePlate());

        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new RuntimeException("Bu plaka sistemde zaten kayıtlı!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        Vehicle vehicle = vehicleMapper.toEntity(request);
        vehicle.setUser(user);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(savedVehicle);
    }

    @Override
    public List<VehicleResponse> getUserVehicles(Long userId) {
        return vehicleRepository.findAllByUserId(userId).stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı!"));
        return vehicleMapper.toResponse(vehicle);
    }

    // YENİ EKLENEN: Update Metodu
    @Override
    @Transactional
    public VehicleResponse updateVehicle(Long vehicleId, VehicleCreateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Güncellenecek araç bulunamadı!"));

        // Plaka değişmişse ve yeni plaka başkasına aitse engelle (Mühendislik Detayı)
        if (!vehicle.getLicensePlate().equals(request.getLicensePlate()) &&
                vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new RuntimeException("Hata: Girdiğiniz yeni plaka başka bir araca ait!");
        }

        // Değerleri Güncelle (ID, User ve CreatedAt alanlarına dokunmuyoruz)
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setProductionYear(request.getProductionYear());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setCurrentOdometer(request.getCurrentOdometer());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        log.info("{} ID'li araç başarıyla güncellendi.", vehicleId);

        return vehicleMapper.toResponse(updatedVehicle);
    }

    @Override
    @Transactional
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Silinecek araç bulunamadı!"));
        vehicleRepository.delete(vehicle);
        log.info("{} ID'li araç silindi.", vehicleId);
    }
}