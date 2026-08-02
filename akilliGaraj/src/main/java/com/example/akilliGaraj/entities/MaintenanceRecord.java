package com.example.akilliGaraj.entities;



import com.example.akilliGaraj.enums.MaintenanceType;
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
@Table(name = "maintenance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MaintenanceRecord extends com.example.akilliGaraj.entity.BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MaintenanceType type;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal cost;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;


    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @Column(name = "next_due_odometer")
    private Integer nextDueOdometer;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    private LocalDate maintenanceDate;
    private Integer odometerAtMaintenance;
}