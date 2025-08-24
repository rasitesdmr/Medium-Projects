package com.rasitesdmr.bookingservice.dto.response;

import com.rasitesdmr.bookingservice.enums.BookingStatus;
import com.rasitesdmr.bookingservice.enums.StepState;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookingResponse {

    private UUID bookingId;
    private UUID sagaId;
    private BookingStatus status;
    private StepState paymentState;
    private StepState flightState;
    private StepState hotelState;
}
