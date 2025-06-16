package com.smartoffice.assistant.repository;

import com.smartoffice.assistant.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findByFloor(Integer floor);
    List<ParkingSlot> findByAvailable(Boolean available);
    ParkingSlot findBySlotId(String slotId);

    long countByAvailable(boolean b);
}