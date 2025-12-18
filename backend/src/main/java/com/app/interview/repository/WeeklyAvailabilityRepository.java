package com.app.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;

import com.app.interview.model.WeeklyAvailability;

public interface WeeklyAvailabilityRepository
        extends JpaRepository<WeeklyAvailability, Long> {

    boolean existsByDayOfWeek(DayOfWeek dayOfWeek);
}
