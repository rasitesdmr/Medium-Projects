package com.rasitesdmr.bookingservice.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CreateBookingRequest {
    private String userId;
    private BigDecimal amountTotal;
    private String flightFrom;
    private String flightTo;
    private LocalDate flightDate;
    private String flightNo;
    private String hotelCode;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
