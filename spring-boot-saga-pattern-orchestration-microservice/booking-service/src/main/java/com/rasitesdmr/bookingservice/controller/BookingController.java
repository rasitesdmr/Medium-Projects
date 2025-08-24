package com.rasitesdmr.bookingservice.controller;

import com.rasitesdmr.bookingservice.dto.request.CreateBookingRequest;
import com.rasitesdmr.bookingservice.dto.response.CreateBookingResponse;
import com.rasitesdmr.bookingservice.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v3/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "")
    public ResponseEntity<CreateBookingResponse> createBooking(@RequestBody CreateBookingRequest createBookingRequest) {
        return new ResponseEntity<>(bookingService.createBooking(createBookingRequest), HttpStatus.CREATED);
    }
}
