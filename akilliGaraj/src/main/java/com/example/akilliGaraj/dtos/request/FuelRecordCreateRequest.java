package com.example.akilliGaraj.dtos.request;



import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
public class FuelRecordCreateRequest {

    @NotNull(message = "Araç ID alanı boş bırakılamaz.")
    private Long vehicleId;

    @NotNull(message = "Alınan litre alanı zorunludur.")
    @DecimalMin(value = "0.1", message = "Alınan yakıt miktarı en az 0.1 litre olmalıdır.")
    private BigDecimal liters;

    @NotNull(message = "Litre fiyatı zorunludur.")
    @Positive(message = "Litre fiyatı pozitif bir değer olmalıdır.")
    private BigDecimal pricePerLiter;

    @NotNull(message = "Yakıt alındığı anki kilometre bilgisi zorunludur.")
    @Positive(message = "Kilometre değeri pozitif bir sayı olmalıdır.")
    private Integer odometerAtFill;

    @NotNull(message = "Deponun tam dolup dolmadığı belirtilmelidir (true/false).")
    private Boolean isFullTank;

    @NotNull(message = "Yakıt alım tarihi zorunludur.")
    @PastOrPresent(message = "Gelecekteki bir tarihe yakıt kaydı girilemez.")
    private LocalDate fillDate;
}