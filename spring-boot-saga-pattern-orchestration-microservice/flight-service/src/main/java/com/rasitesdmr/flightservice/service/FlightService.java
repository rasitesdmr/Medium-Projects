package com.rasitesdmr.flightservice.service;

import com.rasitesdmr.flightservice.model.Flight;
import kafka.event.FlightStartEvent;
import kafka.event.HotelFlightReverseStartEvent;

import java.util.UUID;

public interface FlightService {

    void saveFlight(Flight flight);
    Flight getByBookingId(UUID bookingId);
    void createFlight(FlightStartEvent flightStartEvent, UUID sagaId);
    void handleHotelFlightReverseStart(HotelFlightReverseStartEvent hotelFlightReverseStartEvent, UUID sagaId);
}
