-- Delete from child tables first
DELETE FROM booking;
DELETE FROM room_equipment;

-- Then delete from parent table
DELETE FROM meeting_room;

-- Parking Slots
DELETE FROM parking_slot;

-- Parking Slots
INSERT INTO parking_slot (slot_id, floor, section, number, available, vehicle_type) VALUES
('P1-01', 1, 'A', 1, true, 'CAR'),
('P1-02', 1, 'A', 2, true, 'CAR'),
('P1-03', 1, 'B', 3, true, 'BIKE'),
('P2-01', 2, 'A', 1, true, 'ELECTRIC');

-- Meeting Rooms (insert first to generate IDs)
INSERT INTO meeting_room (id, name, floor, capacity) VALUES
(1, 'Conference Room A', 1, 10),
(2, 'Conference Room B', 1, 8),
(3, 'Board Room', 2, 15);

-- Room Equipment (now the referenced room_id exists)
INSERT INTO room_equipment (room_id, equipment) VALUES
(1, 'PROJECTOR'), (1, 'WHITEBOARD'), (1, 'VIDEO_CONFERENCING'),
(2, 'PROJECTOR'), (2, 'WHITEBOARD'),
(3, 'PROJECTOR'), (3, 'SMART_BOARD'), (3, 'VIDEO_CONFERENCING');
