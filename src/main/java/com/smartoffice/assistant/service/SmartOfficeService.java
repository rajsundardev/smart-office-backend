package com.smartoffice.assistant.service;

import com.smartoffice.assistant.model.Attendance;
import com.smartoffice.assistant.model.ParkingSlot;
import com.smartoffice.assistant.model.MeetingRoom;
import com.smartoffice.assistant.model.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SmartOfficeService {
    // Attendance Services
    Attendance markAttendance(String employeeId, Attendance.AttendanceStatus status, Attendance.TransportMode transportMode);
    List<Attendance> getAttendanceHistory(String employeeId, LocalDate startDate, LocalDate endDate);

    // Parking Services
    List<ParkingSlot> getAllParkingSlots();
    List<ParkingSlot> getParkingSlotsByFloor(Integer floor);
    ParkingSlot reserveParkingSlot(String slotId, String employeeId, LocalDateTime until);
    ParkingSlot freeParkingSlot(String slotId);

    // Meeting Room Services
    List<MeetingRoom> getAllMeetingRooms();
    List<MeetingRoom> getAvailableMeetingRooms(LocalDateTime startTime, LocalDateTime endTime, Integer capacity, List<String> equipment);
    Booking bookMeetingRoom(Long roomId, String title, LocalDateTime startTime, LocalDateTime endTime, String bookedBy);
    void cancelBooking(Long bookingId);

    // Admin Dashboard Services
    Map<String, Object> getOfficeStatistics(LocalDate date);
}

