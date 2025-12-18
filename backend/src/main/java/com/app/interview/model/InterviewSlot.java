package com.app.interview.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InterviewSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int capacity;
    private int booked;

    // ✅ ADD THIS
    
    
    @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "availability_id")
@JsonBackReference
private WeeklyAvailability availability;

    public Long getId() { return id; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getCapacity() { return capacity; }
    public int getBooked() { return booked; }
    public WeeklyAvailability getAvailability() { return availability; }

    public void setId(Long id) { this.id = id; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setBooked(int booked) { this.booked = booked; }
    public void setAvailability(WeeklyAvailability availability) { this.availability = availability; }
}
