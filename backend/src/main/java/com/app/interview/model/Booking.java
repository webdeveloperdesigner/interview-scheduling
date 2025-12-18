package com.app.interview.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "booking",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"candidateEmail"})
    }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateEmail;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private InterviewSlot slot;

    // ===== GETTERS =====
    public Long getId() {
        return id;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public InterviewSlot getSlot() {
        return slot;
    }

    // ===== SETTERS =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public void setSlot(InterviewSlot slot) {
        this.slot = slot;
    }
}
