package com.smartoffice.assistant.repository;

import com.smartoffice.assistant.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    List<MeetingRoom> findByFloor(Integer floor);
    List<MeetingRoom> findByCapacityGreaterThanEqual(Integer capacity);

    @Query("SELECT r FROM MeetingRoom r WHERE :equipment MEMBER OF r.equipment")
    List<MeetingRoom> findByEquipmentContaining(String equipment);

    // Find available rooms between time range
    @Query("SELECT r FROM MeetingRoom r WHERE r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE " +
            "b.startTime < :endTime AND b.endTime > :startTime)")
    List<MeetingRoom> findAvailableRoomsBetweenTimes(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // Find available rooms with capacity and equipment filters
    @Query("SELECT r FROM MeetingRoom r WHERE " +
            "r.capacity >= :minCapacity AND " +
            "r.id NOT IN (SELECT b.room.id FROM Booking b WHERE " +
            "b.startTime < :endTime AND b.endTime > :startTime) AND " +
            "(COALESCE(:equipmentList) IS NULL OR " +
            "EXISTS (SELECT 1 FROM r.equipment e WHERE e IN :equipmentList))")
    List<MeetingRoom> findAvailableRoomsWithFilters(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("minCapacity") Integer minCapacity,
            @Param("equipmentList") List<String> equipmentList);
}
