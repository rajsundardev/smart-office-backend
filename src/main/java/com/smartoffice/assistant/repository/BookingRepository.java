// src/main/java/com/smartoffice/backend/repository/BookingRepository.java
package com.smartoffice.assistant.repository;

import com.smartoffice.assistant.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings for a specific room
    List<Booking> findByRoomId(Long roomId);

    // Find overlapping bookings for a room
    @Query("SELECT b FROM Booking b WHERE " +
            "b.room.id = :roomId AND " +
            "b.startTime < :endTime AND b.endTime > :startTime")
    List<Booking> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // Find all bookings between time range
    List<Booking> findByStartTimeBetweenOrEndTimeBetween(
            LocalDateTime startTime1, LocalDateTime endTime1,
            LocalDateTime startTime2, LocalDateTime endTime2);
}