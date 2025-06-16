package com.smartoffice.assistant.controller;

import com.smartoffice.assistant.exception.ResourceNotFoundException;
import com.smartoffice.assistant.exception.RoomNotAvailableException;
import com.smartoffice.assistant.model.Booking;
import com.smartoffice.assistant.model.MeetingRoom;
import com.smartoffice.assistant.service.SmartOfficeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MeetingRoomController {

    private static final Logger log = LoggerFactory.getLogger(MeetingRoomController.class);
    private final SmartOfficeService smartOfficeService;

    public MeetingRoomController(SmartOfficeService smartOfficeService) {
        this.smartOfficeService = smartOfficeService;
    }

    @GetMapping("/meeting-rooms")
    public ResponseEntity<List<MeetingRoom>> getMeetingRooms() {
        log.info("Fetching all meeting rooms :: ", smartOfficeService.getAllMeetingRooms());
        return ResponseEntity.ok(smartOfficeService.getAllMeetingRooms());
    }

    @GetMapping("/meeting-rooms/available")
    public ResponseEntity<List<MeetingRoom>> getAvailableMeetingRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) List<String> equipment) {
        return ResponseEntity.ok(smartOfficeService.getAvailableMeetingRooms(
                startTime,
                endTime,
                capacity != null ? capacity : 1,
                equipment
        ));
    }

    @PostMapping("/meeting-rooms/book")
    public ResponseEntity<Booking> bookMeetingRoom(
            @RequestParam Long roomId,
            @RequestParam String title,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam String bookedBy) {
        try {
            return ResponseEntity.ok(smartOfficeService.bookMeetingRoom(
                    roomId, title, startTime, endTime, bookedBy
            ));
        } catch (RoomNotAvailableException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        try {
            smartOfficeService.cancelBooking(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
