CREATE TABLE IF NOT EXISTS parking_slot (
    slot_id VARCHAR(20) PRIMARY KEY,
    floor INT,
    section VARCHAR(10),
    number INT,
    available BOOLEAN,
    vehicle_type VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS meeting_room (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    floor INT,
    capacity INT
);
CREATE TABLE IF NOT EXISTS room_equipment (
    id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    equipment VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS attendance (
    id BIGSERIAL PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    transport_mode VARCHAR(50),
    check_in_time TIMESTAMP NOT NULL,
    check_out_time TIMESTAMP
);


