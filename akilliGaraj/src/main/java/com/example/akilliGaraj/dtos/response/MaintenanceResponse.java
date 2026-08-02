package com.example.akilliGaraj.dtos.response;



import com.example.akilliGaraj.enums.MaintenanceType;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MaintenanceResponse {
    private Long id;
    private Long vehicleId;
    private MaintenanceType type;
    private String description;
    private BigDecimal cost;
    private LocalDate recordDate;
    private LocalDate nextDueDate;
    private Integer nextDueOdometer;
    private Integer odometerAtMaintenance;
}