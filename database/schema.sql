-- ============================================
-- Database: interview_scheduler
-- Tables for Interview Scheduling System
-- ============================================

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS interview_slot;
DROP TABLE IF EXISTS weekly_availability;

-- ============================================
-- Table: weekly_availability
-- Stores interviewers' weekly availability
-- ============================================
CREATE TABLE weekly_availability (
    id INT AUTO_INCREMENT PRIMARY KEY,
    interviewer_name VARCHAR(100) NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,  -- Monday, Tuesday, etc.
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Table: interview_slot
-- Stores generated interview slots based on availability
-- ============================================
CREATE TABLE interview_slot (
    id INT AUTO_INCREMENT PRIMARY KEY,
    interviewer_id INT NOT NULL,
    slot_date DATE NOT NULL,
    slot_time TIME NOT NULL,
    is_booked TINYINT(1) DEFAULT 0,  -- 0 = free, 1 = booked
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (interviewer_id) REFERENCES weekly_availability(id)
        ON DELETE CASCADE
);

-- ============================================
-- Table: booking
-- Stores candidate bookings for interview slots
-- ============================================
CREATE TABLE booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    slot_id INT NOT NULL,
    candidate_name VARCHAR(100) NOT NULL,
    candidate_email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (slot_id) REFERENCES interview_slot(id)
        ON DELETE CASCADE
);

-- ============================================
-- Sample Data (Optional)
-- ============================================

-- Weekly availability
INSERT INTO weekly_availability (interviewer_name, day_of_week, start_time, end_time) VALUES
('Alice Johnson', 'Monday', '09:00:00', '12:00:00'),
('Alice Johnson', 'Wednesday', '13:00:00', '17:00:00'),
('Bob Smith', 'Tuesday', '10:00:00', '14:00:00'),
('Bob Smith', 'Thursday', '09:00:00', '12:00:00');

-- Interview slots
INSERT INTO interview_slot (interviewer_id, slot_date, slot_time, is_booked) VALUES
(1, '2025-12-22', '09:00:00', 0),
(1, '2025-12-22', '09:30:00', 0),
(2, '2025-12-24', '13:00:00', 0),
(3, '2025-12-23', '10:00:00', 0);

-- Bookings
INSERT INTO booking (slot_id, candidate_name, candidate_email) VALUES
(1, 'Vivek Kumar', 'vivek@example.com'),
(3, 'Priya Sharma', 'priya@example.com');
