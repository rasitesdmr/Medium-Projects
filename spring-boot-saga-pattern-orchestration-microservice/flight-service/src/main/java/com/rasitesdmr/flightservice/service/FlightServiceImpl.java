package com.rasitesdmr.flightservice.service;

import com.rasitesdmr.flightservice.enums.FlightStatus;
import com.rasitesdmr.flightservice.model.Flight;
import com.rasitesdmr.flightservice.producer.FlightEventProducer;
import com.rasitesdmr.flightservice.repository.FlightRepository;
import kafka.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FlightServiceImpl implements FlightService {

    private final static Logger log = LoggerFactory.getLogger(FlightServiceImpl.class);
    private final FlightRepository flightRepository;
    private final FlightEventProducer flightEventProducer;

    public FlightServiceImpl(FlightRepository flightRepository, FlightEventProducer flightEventProducer) {
        this.flightRepository = flightRepository;
        this.flightEventProducer = flightEventProducer;
    }

    @Override
    public void saveFlight(Flight flight) {
        try {
            flightRepository.save(flight);
        } catch (Exception exception) {
            log.error("Flight save failed : {}", exception.getMessage());
        }
    }

    @Override
    public Flight getByBookingId(UUID bookingId) {
        return flightRepository.findByBookingId(bookingId);
    }

    @Override
    public void createFlight(FlightStartEvent flightStartEvent, UUID sagaId) {
        Flight flight = buildFlightPendingObjectFromFlightStartEvent(flightStartEvent, sagaId);
        saveFlight(flight);
        processBookingBasedOnFlightTo(flight);
    }

    @Override
    public void handleHotelFlightReverseStart(HotelFlightReverseStartEvent hotelFlightReverseStartEvent, UUID sagaId) {
        final UUID bookingId = hotelFlightReverseStartEvent.getBookingId();
        Flight flight = getByBookingId(bookingId);
        updateHotelFlightReverseByPaymentStatus(flight, hotelFlightReverseStartEvent);
        HotelFlightReversedEvent hotelFlightReversedEvent = buildHotelFlightReversedEventObjectFromFlight(flight);
        flightEventProducer.producerHotelFlightReversedEvent(hotelFlightReversedEvent, sagaId);
    }

    private void updateHotelFlightReverseByPaymentStatus(Flight flight, HotelFlightReverseStartEvent hotelFlightReverseStartEvent) {
        final String flightStatus = hotelFlightReverseStartEvent.getFlightStatus();
        flight.setStatus(FlightStatus.valueOf(flightStatus));
        saveFlight(flight);
    }

    private HotelFlightReversedEvent buildHotelFlightReversedEventObjectFromFlight(Flight flight){
        final UUID flightId = flight.getId();
        final UUID bookingId = flight.getBookingId();
        final String userId = flight.getUserId();
        final String flightStatus = flight.getStatus().name();
        return HotelFlightReversedEvent.builder()
                .flightId(flightId)
                .bookingId(bookingId)
                .userId(userId)
                .flightStatus(flightStatus)
                .build();
    }

    private Flight buildFlightPendingObjectFromFlightStartEvent(FlightStartEvent flightStartEvent, UUID sagaId) {
        final UUID bookingId = flightStartEvent.getBookingId();
        final String userId = flightStartEvent.getUserId();
        final String flightFrom = flightStartEvent.getFlightFrom();
        final String flightTo = flightStartEvent.getFlightTo();
        final LocalDate flightDate = flightStartEvent.getFlightDate();
        final String flightNo = flightStartEvent.getFlightNo();
        final FlightStatus status = FlightStatus.PENDING;
        return Flight.builder()
                .bookingId(bookingId)
                .sagaId(sagaId)
                .userId(userId)
                .flightFrom(flightFrom)
                .flightTo(flightTo)
                .flightDate(flightDate)
                .flightNo(flightNo)
                .status(status)
                .build();
    }

    private void processBookingBasedOnFlightTo(Flight flight) {
        String flightTo = flight.getFlightTo();
        if (!flightTo.equals("ANKARA")) {
            processFlightConfirmed(flight);
        } else {
            processFlightFailed(flight);
        }
    }

    private void processFlightConfirmed(Flight flight) {
        final UUID sagaId = flight.getSagaId();
        final FlightStatus flightStatus = FlightStatus.CONFIRMED;
        flight.setStatus(flightStatus);
        saveFlight(flight);
        FlightConfirmedEvent flightConfirmedEvent = buildFlightConfirmedEventObjectFromFlight(flight);
        flightEventProducer.producerFlightConfirmed(flightConfirmedEvent, sagaId);
    }

    private void processFlightFailed(Flight flight) {
        final UUID sagaId = flight.getSagaId();
        final FlightStatus flightStatus = FlightStatus.FAILED;
        flight.setStatus(flightStatus);
        saveFlight(flight);
        FlightFailedEvent flightFailedEvent = buildFlightFailedEventObjectFromFlight(flight);
        flightEventProducer.producerFlightFailed(flightFailedEvent, sagaId);
    }

    private FlightConfirmedEvent buildFlightConfirmedEventObjectFromFlight(Flight flight) {
        final UUID flightId = flight.getId();
        final UUID bookingId = flight.getBookingId();
        final String userId = flight.getUserId();
        final String flightStatus = flight.getStatus().name();
        return FlightConfirmedEvent.builder()
                .flightId(flightId)
                .bookingId(bookingId)
                .userId(userId)
                .flightStatus(flightStatus)
                .build();
    }

    private FlightFailedEvent buildFlightFailedEventObjectFromFlight(Flight flight) {
        final UUID flightId = flight.getId();
        final UUID bookingId = flight.getBookingId();
        final String userId = flight.getUserId();
        final String flightStatus = flight.getStatus().name();
        final String bookingStatus = flight.getStatus().name();
        return FlightFailedEvent.builder()
                .flightId(flightId)
                .bookingId(bookingId)
                .userId(userId)
                .flightStatus(flightStatus)
                .bookingStatus(bookingStatus)
                .build();
    }
}
