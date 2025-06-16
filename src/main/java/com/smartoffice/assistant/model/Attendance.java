package com.smartoffice.assistant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Enumerated(EnumType.STRING)
    private TransportMode transportMode;

    @Column(nullable = false)
    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    public enum AttendanceStatus {
        WFH, IN_OFFICE, ON_LEAVE
    }

    public enum TransportMode {
        CAR, BIKE, PUBLIC, WALK
    }
}