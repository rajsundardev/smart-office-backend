package com.smartoffice.assistant.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "meeting_room")
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer capacity;

    @ElementCollection
    @CollectionTable(name = "room_equipment", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "equipment")
    private List<String> equipment;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}


