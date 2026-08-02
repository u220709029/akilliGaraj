package com.example.akilliGaraj.dtos.request;



import com.example.akilliGaraj.enums.MaintenanceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MaintenanceCreateRequest {

    @NotNull(message = "Araç ID bilgisi zorunludur.")
    private Long vehicleId;

    @NotNull(message = "Bakım türü belirtilmelidir.")
    private MaintenanceType type;

    @NotBlank(message = "Açıklama boş olamaz. (Örn: Triger değişti)")
    private String description;

    @NotNull(message = "Maliyet boş olamaz.")
    @DecimalMin(value = "0.0", message = "Maliyet negatif olamaz.")
    private BigDecimal cost;

    @NotNull(message = "İşlem tarihi zorunludur.")
    private LocalDate recordDate;


    private LocalDate nextDueDate;
    private Integer nextDueOdometer;
    private LocalDate maintenanceDate;
    private Integer odometerAtMaintenance;
}