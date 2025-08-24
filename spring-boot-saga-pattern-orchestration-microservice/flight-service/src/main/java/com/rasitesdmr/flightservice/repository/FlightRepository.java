package com.rasitesdmr.flightservice.repository;

import com.rasitesdmr.flightservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Flight findByBookingId(UUID bookingId);
}
