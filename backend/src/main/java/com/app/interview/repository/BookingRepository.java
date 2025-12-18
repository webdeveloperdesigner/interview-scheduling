package com.app.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.interview.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByCandidateEmail(String candidateEmail);
}
