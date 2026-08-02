package com.example.akilliGaraj.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fuel_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FuelRecord extends com.example.akilliGaraj.entity.BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal liters;

    @Column(name = "price_per_liter", nullable = false, precision = 6, scale = 2)
    private BigDecimal pricePerLiter;

    @Column(name = "total_cost", nullable = false, precision = 8, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "odometer_at_fill", nullable = false)
    private Integer odometerAtFill;


    @Column(name = "is_full_tank", nullable = false)
    private Boolean isFullTank;

    @Column(name = "fill_date", nullable = false)
    private LocalDate fillDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}