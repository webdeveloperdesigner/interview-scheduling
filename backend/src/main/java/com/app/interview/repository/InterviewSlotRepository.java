package com.app.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.app.interview.model.InterviewSlot;
import com.app.interview.model.WeeklyAvailability;

public interface InterviewSlotRepository extends JpaRepository<InterviewSlot, Long> {

    List<InterviewSlot> findByAvailability(WeeklyAvailability availability);

    List<InterviewSlot> findByAvailabilityOrderByStartTimeAsc(
            WeeklyAvailability availability
    );

    // ✅ FIX: delete ONLY unbooked slots (prevents FK error)
    void deleteByAvailabilityAndBooked(
            WeeklyAvailability availability,
            int booked
    );
}
