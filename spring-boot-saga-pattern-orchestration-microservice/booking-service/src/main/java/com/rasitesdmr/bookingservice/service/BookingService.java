package com.rasitesdmr.bookingservice.service;

import com.rasitesdmr.bookingservice.dto.request.CreateBookingRequest;
import com.rasitesdmr.bookingservice.dto.response.CreateBookingResponse;
import com.rasitesdmr.bookingservice.model.Booking;
import kafka.event.*;

import java.util.UUID;

public interface BookingService {

    void saveBooking(Booking booking);
    CreateBookingResponse createBooking(CreateBookingRequest createBookingRequest);
    void updatePaymentBookingCompletedStatus(PaymentBookingCompletedEvent paymentBookingCompletedEvent);
    void updateFlightBookingConfirmedStatus(FlightBookingConfirmedEvent flightBookingConfirmedEvent);
    void updateHotelBookingBookedStatus(HotelBookingBookedEvent hotelBookingBookedEvent);
    void updateBookingConfirmedStatus(BookingConfirmedEvent bookingConfirmedEvent);
    void updatePaymentBookingFailedStatus(PaymentBookingFailedEvent paymentBookingFailedEvent);
    void updateFlightBookingFailedStatus(FlightBookingFailedEvent flightBookingFailedEvent);
    void updateHotelBookingFailedStatus(HotelBookingFailedEvent hotelBookingFailedEvent);
    Booking getLockByBookingId(UUID bookingId);
}
