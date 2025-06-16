package com.smartoffice.assistant.repository;

import com.smartoffice.assistant.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeIdAndDateBetweenOrderByDateDescCheckInTimeDesc(String employeeId, LocalDate startDate, LocalDate endDate);
    Attendance findByEmployeeIdAndDate(String employeeId, LocalDate date);
    long countByDateAndStatus(LocalDate date, Attendance.AttendanceStatus status);
}