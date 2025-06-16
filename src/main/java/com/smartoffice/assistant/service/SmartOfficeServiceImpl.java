package com.smartoffice.assistant.service;

import com.smartoffice.assistant.exception.ResourceNotFoundException;
import com.smartoffice.assistant.exception.RoomNotAvailableException;
import com.smartoffice.assistant.model.*;
import com.smartoffice.assistant.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@Transactional
public class SmartOfficeServiceImpl implements SmartOfficeService {
    private final AttendanceRepository attendanceRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final BookingRepository bookingRepository;

    public SmartOfficeServiceImpl(
            AttendanceRepository attendanceRepository,
            ParkingSlotRepository parkingSlotRepository,
            MeetingRoomRepository meetingRoomRepository,
            BookingRepository bookingRepository) {
        this.attendanceRepository = attendanceRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Attendance markAttendance(String employeeId, Attendance.AttendanceStatus status, Attendance.TransportMode transportMode) {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDate(LocalDate.now());
        attendance.setStatus(status);
        attendance.setTransportMode(transportMode);
        attendance.setCheckInTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceHistory(String employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEmployeeIdAndDateBetweenOrderByDateDescCheckInTimeDesc(employeeId, startDate, endDate);
    }

    @Override
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }

    @Override
    public List<ParkingSlot> getParkingSlotsByFloor(Integer floor) {
        return parkingSlotRepository.findByFloor(floor);
    }

    @Override
    public List<MeetingRoom> getAvailableMeetingRooms(LocalDateTime startTime, LocalDateTime endTime,
                                                      Integer capacity, List<String> equipment) {
        return meetingRoomRepository.findAvailableRoomsWithFilters(
                startTime,
                endTime,
                capacity,
                equipment
        );
    }

    @Override
    public Booking bookMeetingRoom(Long roomId, String title,
                                   LocalDateTime startTime, LocalDateTime endTime,
                                   String bookedBy) {
        // Check for availability
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                roomId,
                startTime,
                endTime
        );

        if (!overlapping.isEmpty()) {
            throw new RoomNotAvailableException("Room is already booked for the requested time");
        }

        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting room not found"));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setTitle(title);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setBookedBy(bookedBy);

        return bookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking not found");
        }
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public ParkingSlot reserveParkingSlot(String slotId, String employeeId, LocalDateTime until) {
        ParkingSlot slot = parkingSlotRepository.findBySlotId(slotId);
        if (slot != null && slot.getAvailable()) {
            slot.setAvailable(false);
            slot.setBookedBy(employeeId);
            slot.setReservedUntil(until);
            return parkingSlotRepository.save(slot);
        }
        return null;
    }

    @Override
    public ParkingSlot freeParkingSlot(String slotId) {
        ParkingSlot slot = parkingSlotRepository.findBySlotId(slotId);
        if (slot != null && !slot.getAvailable()) {
            slot.setAvailable(true);
            slot.setBookedBy(null);
            slot.setReservedUntil(null);
            return parkingSlotRepository.save(slot);
        }
        return null;
    }

    @Override
    public List<MeetingRoom> getAllMeetingRooms() {
        return meetingRoomRepository.findAll();
    }

    @Override
    public Map<String, Object> getOfficeStatistics(LocalDate date) {
        Map<String, Object> stats = new HashMap<>();

        // Attendance stats
        long totalEmployees = 50; // Would normally come from Employee repository
        long inOfficeToday = attendanceRepository.countByDateAndStatus(date, Attendance.AttendanceStatus.IN_OFFICE);
        stats.put("attendanceRate", (inOfficeToday * 100.0) / totalEmployees);

        // Parking stats
        long totalSlots = parkingSlotRepository.count();
        long occupiedSlots = parkingSlotRepository.countByAvailable(false);
        stats.put("parkingOccupancy", (occupiedSlots * 100.0) / totalSlots);

        // Room utilization (simplified)
        stats.put("roomUtilization", 42.0); // Would calculate based on bookings

        return stats;
    }
}