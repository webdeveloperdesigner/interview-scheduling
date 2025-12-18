package com.app.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import com.app.interview.dto.WeeklyAvailabilityRequest;
import com.app.interview.model.InterviewSlot;
import com.app.interview.model.WeeklyAvailability;
import com.app.interview.repository.WeeklyAvailabilityRepository;
import com.app.interview.service.InterviewService;

@RestController
@RequestMapping("/api")
public class InterviewController {

    @Autowired
    private InterviewService service;

    @Autowired
    private WeeklyAvailabilityRepository availabilityRepo;

    // ================= AVAILABILITY =================

    @GetMapping("/availability")
    public List<WeeklyAvailability> getAllAvailability() {
        return availabilityRepo.findAll();
    }

    @PostMapping("/availability")
public ResponseEntity<String> addAvailability(
        @RequestBody WeeklyAvailabilityRequest req) {

    LocalTime start = LocalTime.parse(req.getStartTime());
    LocalTime end   = LocalTime.parse(req.getEndTime());

    // ❌ BLOCK INVALID TIME
    if (!start.isBefore(end)) {
        return ResponseEntity
                .badRequest()
                .body("Start time must be before end time");
    }

    WeeklyAvailability availability = new WeeklyAvailability();
    availability.setDayOfWeek(
            DayOfWeek.valueOf(req.getDayOfWeek().toUpperCase())
    );
    availability.setStartTime(start);
    availability.setEndTime(end);
    availability.setCapacity(req.getCapacity());

    availabilityRepo.save(availability);

    service.generateSlotsForAvailability(availability);

    return ResponseEntity.ok("Availability added & slots generated");
}


    @PutMapping("/availability/{id}")
public ResponseEntity<String> updateAvailability(
        @PathVariable Long id,
        @RequestBody WeeklyAvailabilityRequest req) {

    LocalTime start = LocalTime.parse(req.getStartTime());
    LocalTime end   = LocalTime.parse(req.getEndTime());

    if (!start.isBefore(end)) {
        return ResponseEntity
                .badRequest()
                .body("Start time must be before end time");
    }

    WeeklyAvailability availability = availabilityRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Availability not found"));

    availability.setDayOfWeek(
            DayOfWeek.valueOf(req.getDayOfWeek().toUpperCase())
    );
    availability.setStartTime(start);
    availability.setEndTime(end);
    availability.setCapacity(req.getCapacity());

    availabilityRepo.save(availability);

    service.generateSlotsForAvailability(availability);

    return ResponseEntity.ok("Availability updated & slots regenerated");
}


    // ================= SLOTS =================

    @GetMapping("/slots/by-availability/{id}")
    public List<InterviewSlot> getSlotsByAvailability(
            @PathVariable Long id) {

        WeeklyAvailability availability = availabilityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        return service.getSlotsForAvailability(availability);
    }

    // ================= BOOKING =================

    @PostMapping("/book/{slotId}")
    public ResponseEntity<String> bookSlot(
            @PathVariable Long slotId,
            @RequestParam String email) {

        service.bookSlot(slotId, email);
        return ResponseEntity.ok("Booked successfully");
    }
}
