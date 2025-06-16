package com.smartoffice.assistant.controller;

import com.smartoffice.assistant.model.ParkingSlot;
import com.smartoffice.assistant.service.SmartOfficeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParkingController {

    private final SmartOfficeService smartOfficeService;

    public ParkingController(SmartOfficeService smartOfficeService) {
        this.smartOfficeService = smartOfficeService;
    }

    @GetMapping("/parking")
    public ResponseEntity<List<ParkingSlot>> getParkingSlots(@RequestParam(required = false) Integer floor) {
        if (floor != null) {
            return ResponseEntity.ok(smartOfficeService.getParkingSlotsByFloor(floor));
        }
        return ResponseEntity.ok(smartOfficeService.getAllParkingSlots());
    }

    @PostMapping("/parking/reserve")
    public ResponseEntity<ParkingSlot> reserveParkingSlot(
            @RequestParam String slotId,
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime until) {
        ParkingSlot slot = smartOfficeService.reserveParkingSlot(slotId, employeeId, until);
        return slot != null ? ResponseEntity.ok(slot) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/parking/free")
    public ResponseEntity<ParkingSlot> freeParkingSlot(@RequestParam String slotId) {
        ParkingSlot slot = smartOfficeService.freeParkingSlot(slotId);
        return slot != null ? ResponseEntity.ok(slot) : ResponseEntity.badRequest().build();
    }
}
