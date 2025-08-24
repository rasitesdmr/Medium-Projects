package com.rasitesdmr.orchestratorservice.service;

import com.rasitesdmr.orchestratorservice.model.SagaBookingSnapshot;
import kafka.event.*;

import java.util.UUID;

public interface OrchestratorService {

    void handleBookingCreated(BookingCreatedEvent bookingCreatedEvent, UUID sagaId);
    void handlePaymentCompleted(PaymentCompletedEvent paymentCompletedEvent, UUID sagaId);
    void handlePaymentFailed(PaymentFailedEvent paymentFailedEvent, UUID sagaId);
    void handleFlightPaymentReversed(FlightPaymentReversedEvent flightPaymentReversedEvent, UUID sagaId);
    void handleHotelPaymentReversed(HotelPaymentReversedEvent hotelPaymentReversedEvent, UUID sagaId);
    void handleFlightConfirmed(FlightConfirmedEvent flightConfirmedEvent, UUID sagaId);
    void handleFlightFailed(FlightFailedEvent flightFailedEvent, UUID sagaId);
    void handleHotelFailed(HotelFailedEvent hotelFailedEvent, UUID sagaId);
    void handleHotelBooked(HotelBookedEvent hotelBookedEvent, UUID sagaId);
    void handleHotelFlightReversed(HotelFlightReversedEvent hotelFlightReversedEvent, UUID sagaId);
    SagaBookingSnapshot getByBookingId(UUID bookingId);
}
