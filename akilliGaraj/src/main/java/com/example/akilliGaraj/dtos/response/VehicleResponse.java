package com.example.akilliGaraj.dtos.response;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponse {
    private Long id;
    private String brand;
    private String model;
    private Integer productionYear;
    private String licensePlate;
    private Integer currentOdometer;
}