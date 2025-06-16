package com.smartoffice.assistant.controller;

import com.smartoffice.assistant.model.Attendance;
import com.smartoffice.assistant.service.SmartOfficeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    private final SmartOfficeService smartOfficeService;

    public AttendanceController(SmartOfficeService smartOfficeService) {
        this.smartOfficeService = smartOfficeService;
    }

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> markAttendance(
            @RequestParam String employeeId,
            @RequestParam Attendance.AttendanceStatus status,
            @RequestParam(required = false) Attendance.TransportMode transportMode) {
        return ResponseEntity.ok(smartOfficeService.markAttendance(employeeId, status, transportMode));
    }

    @GetMapping("/attendance/history")
    public ResponseEntity<List<Attendance>> getAttendanceHistory(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(smartOfficeService.getAttendanceHistory(employeeId, startDate, endDate));
    }
}
