package com.rasitesdmr.orchestratorservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "saga_booking_snapshot")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SagaBookingSnapshot {

    @Id
    private UUID bookingId;
    private UUID sagaId;
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
