package com.example.akilliGaraj.services;

import com.example.akilliGaraj.dtos.request.FuelRecordCreateRequest;
import com.example.akilliGaraj.dtos.response.FuelRecordResponse;
import com.example.akilliGaraj.dtos.response.FuelSummaryResponse;
import com.example.akilliGaraj.entities.FuelRecord;
import com.example.akilliGaraj.entities.Vehicle;
import com.example.akilliGaraj.mapper.FuelRecordMapper;
import com.example.akilliGaraj.repository.FuelRecordRepository;
import com.example.akilliGaraj.repository.VehicleRepository;
import com.example.akilliGaraj.service.FuelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuelServiceImpl implements FuelService {

    private final FuelRecordRepository fuelRecordRepository;
    private final VehicleRepository vehicleRepository;
    private final FuelRecordMapper fuelRecordMapper;

    @Override
    @Transactional
    public FuelRecordResponse addFuelRecord(FuelRecordCreateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı!"));

        if (request.getOdometerAtFill() < vehicle.getCurrentOdometer()) {
            throw new RuntimeException("Girilen kilometre, aracın güncel kilometresinden düşük olamaz!");
        }

        FuelRecord record = fuelRecordMapper.toEntity(request);
        record.setVehicle(vehicle);
        record.setTotalCost(request.getLiters().multiply(request.getPricePerLiter()));

        vehicle.setCurrentOdometer(request.getOdometerAtFill());
        vehicleRepository.save(vehicle);

        FuelRecord savedRecord = fuelRecordRepository.save(record);
        return fuelRecordMapper.toResponse(savedRecord);
    }



    // YENİ EKLENEN: Tekil Getirme Metodu
    @Override
    public FuelRecordResponse getFuelRecordById(Long recordId) {
        FuelRecord record = fuelRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Yakıt kaydı bulunamadı!"));
        return fuelRecordMapper.toResponse(record);
    }

    // YENİ EKLENEN: Update Metodu
    @Override
    @Transactional
    public FuelRecordResponse updateFuelRecord(Long recordId, FuelRecordCreateRequest request) {
        FuelRecord record = fuelRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Güncellenecek yakıt kaydı bulunamadı!"));

        // Değerleri Güncelle
        record.setLiters(request.getLiters());
        record.setPricePerLiter(request.getPricePerLiter());

        // DİKKAT: Litre veya fiyat değiştiyse toplam maliyeti yeniden hesaplıyoruz
        record.setTotalCost(request.getLiters().multiply(request.getPricePerLiter()));

        record.setOdometerAtFill(request.getOdometerAtFill());
        record.setIsFullTank(request.getIsFullTank());
        record.setFillDate(request.getFillDate());

        FuelRecord updatedRecord = fuelRecordRepository.save(record);
        log.info("{} ID'li yakıt kaydı güncellendi.", recordId);

        return fuelRecordMapper.toResponse(updatedRecord);
    }

    // YENİ EKLENEN: Delete Metodu
    @Override
    @Transactional
    public void deleteFuelRecord(Long recordId) {
        FuelRecord record = fuelRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Silinecek yakıt kaydı bulunamadı!"));

        fuelRecordRepository.delete(record);
        log.info("{} ID'li yakıt kaydı silindi.", recordId);
    }

    @Override
    public String calculateAverageConsumption(Long vehicleId) {
        List<FuelRecord> lastTwoFullTanks = fuelRecordRepository
                .findByVehicleIdAndIsFullTankTrueOrderByOdometerAtFillDesc(vehicleId, PageRequest.of(0, 2));

        if (lastTwoFullTanks.size() < 2) {
            return "Hesaplama için en az 2 'Full Depo' kaydı gereklidir.";
        }

        FuelRecord latestFill = lastTwoFullTanks.get(0);
        FuelRecord previousFill = lastTwoFullTanks.get(1);

        int distanceCovered = latestFill.getOdometerAtFill() - previousFill.getOdometerAtFill();

        if (distanceCovered <= 0) return "Kilometre farkı geçersiz.";

        double litersUsed = latestFill.getLiters().doubleValue();
        double consumption = (litersUsed / distanceCovered) * 100;

        return String.format("Son %d km baz alınarak ortalama tüketim: %.2f L/100km", distanceCovered, consumption);
    }
    @Override
    public List<FuelRecordResponse> getVehicleFuelHistory(Long vehicleId) {
        // Aracın tüm yakıt geçmişini tarihe göre yeniden eskiye sıralı çeker
        List<FuelRecord> history = fuelRecordRepository.findByVehicleIdOrderByOdometerAtFillDesc(vehicleId);

        return history.stream()
                .map(fuelRecordMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FuelSummaryResponse getVehicleFuelSummary(Long vehicleId) {
        // 1. İstatistik için son 2 full depo kaydını çekiyoruz
        List<FuelRecord> lastTwoFullTanks = fuelRecordRepository
                .findByVehicleIdAndIsFullTankTrueOrderByOdometerAtFillDesc(vehicleId, PageRequest.of(0, 2));

        // 2. Tabloda göstermek üzere aracın TÜM yakıt geçmişini çekiyoruz
        List<FuelRecordResponse> fullHistory = getVehicleFuelHistory(vehicleId);

        // Değişkenleri başlangıç değerleriyle tanımlayalım
        Integer totalDistance = 0;
        BigDecimal totalSpent = BigDecimal.ZERO;
        BigDecimal averageConsumption = BigDecimal.ZERO;
        BigDecimal costPerKm = BigDecimal.ZERO;

        // Bugüne kadar bu araç için harcanan TOPLAM parayı tüm geçmişten hesapla
        if (!fullHistory.isEmpty()) {
            totalSpent = fullHistory.stream()
                    .map(FuelRecordResponse::getTotalCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        // Eğer en az 2 full depo kaydı varsa tüketim istatistiklerini hesapla
        if (lastTwoFullTanks.size() >= 2) {
            FuelRecord latestFill = lastTwoFullTanks.get(0);
            FuelRecord previousFill = lastTwoFullTanks.get(1);

            totalDistance = latestFill.getOdometerAtFill() - previousFill.getOdometerAtFill();

            if (totalDistance > 0) {
                BigDecimal distanceDecimal = new BigDecimal(totalDistance);

                // Ortalama Tüketim: (Son Alınan Litre / Yapılan KM) * 100
                averageConsumption = latestFill.getLiters()
                        .divide(distanceDecimal, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .setScale(2, RoundingMode.HALF_UP);

                // Son dolum maliyeti
                BigDecimal latestFillCost = latestFill.getPricePerLiter().multiply(latestFill.getLiters());

                // KM Başına Maliyet: (Son Dolum Maliyeti / Yapılan KM)
                costPerKm = latestFillCost.divide(distanceDecimal, 2, RoundingMode.HALF_UP);
            }
        }

        // Hazırladığımız tüm verileri tek paket halinde DTO'ya sarıp gönderiyoruz
        return FuelSummaryResponse.builder()
                .fuelRecords(fullHistory)          // Tüm geçmiş liste
                .totalDistance(totalDistance)      // Son iki dolum arası mesafe
                .totalSpent(totalSpent)            // Genel toplam harcanan TL
                .averageConsumption(averageConsumption) // L/100km
                .costPerKm(costPerKm)              // TL/km
                .build();
    }
}