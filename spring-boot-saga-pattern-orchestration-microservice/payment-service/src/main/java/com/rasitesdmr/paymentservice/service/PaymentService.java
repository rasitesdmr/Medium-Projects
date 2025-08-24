package com.rasitesdmr.paymentservice.service;

import com.rasitesdmr.paymentservice.model.Payment;
import kafka.event.FlightPaymentReverseStartEvent;
import kafka.event.HotelPaymentReverseStartEvent;
import kafka.event.PaymentStartEvent;

import java.util.UUID;

public interface PaymentService {

    void savePayment(Payment payment);
    Payment getPaymentByBookingId(UUID bookingId);
    void createPayment(PaymentStartEvent paymentStartEvent, UUID sagaId);
    void handleFlightPaymentReverseStart(FlightPaymentReverseStartEvent flightPaymentReverseStartEvent, UUID sagaId);
    void handleHotelPaymentReverseStart(HotelPaymentReverseStartEvent hotelPaymentReverseStartEvent, UUID sagaId);
}
