package com.example.akilliGaraj.dtos.response;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelRecordResponse {
    private Long id;
    private Long vehicleId;
    private BigDecimal liters;
    private BigDecimal pricePerLiter;
    private BigDecimal totalCost;
    private Integer odometerAtFill;
    private Boolean isFullTank;
    private LocalDate fillDate;
}