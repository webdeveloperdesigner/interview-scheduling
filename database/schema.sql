CREATE DATABASE IF NOT EXISTS interview_db;
USE interview_db;

DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS interview_slot;
DROP TABLE IF EXISTS weekly_availability;

CREATE TABLE weekly_availability (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    day_of_week ENUM(
        'MONDAY','TUESDAY','WEDNESDAY',
        'THURSDAY','FRIDAY','SATURDAY','SUNDAY'
    ) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE interview_slot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    capacity INT NOT NULL,
    booked INT NOT NULL,
    availability_id BIGINT,
    CONSTRAINT fk_slot_availability
        FOREIGN KEY (availability_id)
        REFERENCES weekly_availability(id)
        ON DELETE CASCADE
);

CREATE TABLE booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_email VARCHAR(255) NOT NULL UNIQUE,
    slot_id BIGINT,
    CONSTRAINT fk_booking_slot
        FOREIGN KEY (slot_id)
        REFERENCES interview_slot(id)
        ON DELETE CASCADE
);
