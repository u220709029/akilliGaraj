package com.example.akilliGaraj.dtos.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleCreateRequest {



    @NotBlank(message = "Marka bilgisi boş olamaz.")
    private String brand;

    @NotBlank(message = "Model bilgisi boş olamaz.")
    private String model;

    @NotNull(message = "Üretim yılı zorunludur.")
    @Min(value = 1950, message = "Çok eski bir tarih girdiniz.")
    @Max(value = 2026, message = "Gelecekteki bir yılı giremezsiniz.")
    private Integer productionYear;

    @NotBlank(message = "Plaka boş bırakılamaz.")
    private String licensePlate;

    @NotNull(message = "Güncel kilometre bilgisi zorunludur.")
    @Min(value = 0, message = "Kilometre negatif olamaz.")
    private Integer currentOdometer;
}