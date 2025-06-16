package com.smartoffice.assistant.controller;

import com.smartoffice.assistant.service.SmartOfficeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminController {
    private final SmartOfficeService smartOfficeService;

    public AdminController(SmartOfficeService smartOfficeService) {
        this.smartOfficeService = smartOfficeService;
    }


    @GetMapping("/admin/stats")
    public ResponseEntity<Map<String, Object>> getOfficeStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(smartOfficeService.getOfficeStatistics(date));
    }
}
