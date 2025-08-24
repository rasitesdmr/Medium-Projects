package com.rasitesdmr.bookingservice.model;

import com.rasitesdmr.bookingservice.enums.BookingStatus;
import com.rasitesdmr.bookingservice.enums.StepState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false, updatable = false)
    private UUID sagaId; // Rezervasyonun bağlı olduğu saga sürecinin benzersiz kimliğidir. Tum adımlar bu kimlik üzerinden izlenir.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; //Rezervasyonun genel durumu.

    @Column(nullable = false)
    private BigDecimal amountTotal; // Rezervasyonun toplam tutarı

    @Column(nullable = false)
    private String flightFrom; // Uçuşun kalkış havalimanı kodu

    @Column(nullable = false)
    private String flightTo; // Uçuşun varış havalimanı kodu

    @Column(nullable = false)
    private LocalDate flightDate; //Uçuşun yapılacağı tarih.

    @Column(length = 16)
    private String flightNo; //Uçuş numarası

    @Column(nullable = false)
    private String hotelCode; //Otelin sistemdeki benzersiz kodu.

    @Column(nullable = false)
    private LocalDate checkIn; // Otel giriş tarihi.

    @Column(nullable = false)
    private LocalDate checkOut; // Otel çıkış tarihi.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StepState paymentState; // Ödeme adımının durumu

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StepState flightState; // Uçuş adımının durumu

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StepState hotelState; // Otel adımının durumu

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt; // Rezervasyon kaydının oluşturulma zamanı.

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt; // Rezervasyon kaydının son güncellenme zamanı.

}
