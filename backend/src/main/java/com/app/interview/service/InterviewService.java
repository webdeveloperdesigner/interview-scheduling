package com.app.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.app.interview.model.*;
import com.app.interview.repository.*;

@Service
public class InterviewService {

    @Autowired
    private InterviewSlotRepository slotRepo;

    @Autowired
    private WeeklyAvailabilityRepository availabilityRepo;

    @Autowired
    private BookingRepository bookingRepo;

    // =====================================================
    // 🔹 GENERATE SLOTS FOR ONE AVAILABILITY
    // =====================================================
   @Transactional
public void generateSlotsForAvailability(WeeklyAvailability availability) {

    // ✅ delete ONLY unbooked slots (FK safe)
    slotRepo.deleteByAvailabilityAndBooked(availability, 0);

    LocalDate today = LocalDate.now();

    for (int i = 0; i < 14; i++) {

        LocalDate date = today.plusDays(i);

        if (date.getDayOfWeek() != availability.getDayOfWeek()) continue;

        LocalDateTime start =
                LocalDateTime.of(date, availability.getStartTime());

        LocalDateTime end =
                LocalDateTime.of(date, availability.getEndTime());

        while (!start.plusMinutes(30).isAfter(end)) {

            InterviewSlot slot = new InterviewSlot();
            slot.setStartTime(start);
            slot.setEndTime(start.plusMinutes(30));
            slot.setCapacity(availability.getCapacity());
            slot.setBooked(0);
            slot.setAvailability(availability);

            slotRepo.save(slot);
            start = start.plusMinutes(30);
        }
    }
}


    // =====================================================
    // 🔹 GENERATE SLOTS FOR ALL AVAILABILITIES (FIX)
    // =====================================================
    @Transactional
    public void generateSlotsFromAvailability() {
        List<WeeklyAvailability> all = availabilityRepo.findAll();
        for (WeeklyAvailability a : all) {
            generateSlotsForAvailability(a);
        }
    }

    // =====================================================
    // 🔹 GET SLOTS
    // =====================================================
    public List<InterviewSlot> getSlotsForAvailability(
            WeeklyAvailability availability) {

        return slotRepo.findByAvailabilityOrderByStartTimeAsc(availability);
    }

    // =====================================================
    // 🔹 BOOK SLOT
    // =====================================================
    @Transactional
    public void bookSlot(Long slotId, String email) {

        if (bookingRepo.existsByCandidateEmail(email)) {
            throw new RuntimeException("Candidate already booked");
        }

        InterviewSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getBooked() >= slot.getCapacity()) {
            throw new RuntimeException("Slot full");
        }

        slot.setBooked(slot.getBooked() + 1);
        slotRepo.save(slot);

        Booking booking = new Booking();
        booking.setCandidateEmail(email);
        booking.setSlot(slot);
        bookingRepo.save(booking);
    }
}
