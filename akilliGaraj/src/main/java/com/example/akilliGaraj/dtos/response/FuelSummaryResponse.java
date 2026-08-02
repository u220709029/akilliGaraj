package com.example.akilliGaraj.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelSummaryResponse {
    private List<FuelRecordResponse> fuelRecords;
    private Integer totalDistance;
    private BigDecimal totalSpent;
    private BigDecimal averageConsumption;
    private BigDecimal costPerKm;
}