package com.smartoffice.assistant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "parking_slot")
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slotId;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Boolean available = true;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private String bookedBy;
    private LocalDateTime reservedFrom;
    private LocalDateTime reservedUntil;

    public enum VehicleType {
        CAR, BIKE, ELECTRIC, HANDICAP
    }
}
