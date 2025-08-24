package com.rasitesdmr.orchestratorservice.service;

import com.rasitesdmr.orchestratorservice.enums.StepState;
import com.rasitesdmr.orchestratorservice.exception.exceptions.NotFoundException;
import com.rasitesdmr.orchestratorservice.model.SagaBookingSnapshot;
import com.rasitesdmr.orchestratorservice.producer.BookingEventProducer;
import com.rasitesdmr.orchestratorservice.producer.FlightEventProducer;
import com.rasitesdmr.orchestratorservice.producer.HotelEventProducer;
import com.rasitesdmr.orchestratorservice.producer.PaymentEventProducer;
import com.rasitesdmr.orchestratorservice.repository.SagaBookingSnapshotRepository;
import kafka.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class OrchestratorServiceImpl implements OrchestratorService {

    private final static Logger log = LoggerFactory.getLogger(OrchestratorServiceImpl.class);

    private final SagaBookingSnapshotRepository sagaBookingSnapshotRepository;
    private final PaymentEventProducer paymentEventProducer;
    private final BookingEventProducer bookingEventProducer;
    private final FlightEventProducer flightEventProducer;
    private final HotelEventProducer hotelEventProducer;

    public OrchestratorServiceImpl(SagaBookingSnapshotRepository sagaBookingSnapshotRepository, PaymentEventProducer paymentEventProducer, BookingEventProducer bookingEventProducer, FlightEventProducer flightEventProducer, HotelEventProducer hotelEventProducer, HotelEventProducer hotelEventProducer1) {
        this.sagaBookingSnapshotRepository = sagaBookingSnapshotRepository;
        this.paymentEventProducer = paymentEventProducer;
        this.bookingEventProducer = bookingEventProducer;
        this.flightEventProducer = flightEventProducer;
        this.hotelEventProducer = hotelEventProducer1;
    }

    public void saveSagaBookingSnapshot(SagaBookingSnapshot sagaBookingSnapshot) {
        try {
            sagaBookingSnapshotRepository.save(sagaBookingSnapshot);
            log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Saga Booking Snapshot Object Create", sagaBookingSnapshot.getSagaId(), sagaBookingSnapshot.getBookingId());
        } catch (Exception exception) {
            log.error("Saga Booking Snapshot Save Error : {}", exception.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleBookingCreated(BookingCreatedEvent bookingCreatedEvent, UUID sagaId) {
        SagaBookingSnapshot sagaBookingSnapshot = buildSagaBookingSnapshotObjectFromBookingCreatedEvent(bookingCreatedEvent, sagaId);
        saveSagaBookingSnapshot(sagaBookingSnapshot);
        PaymentStartEvent paymentStartEvent = buildPaymentStartEventObjectFromSagaBookingSnapshot(sagaBookingSnapshot);
        paymentEventProducer.producerPaymentStart(paymentStartEvent, sagaId);
    }

    @Override
    public void handlePaymentCompleted(PaymentCompletedEvent paymentCompletedEvent, UUID sagaId) {
        final UUID bookingId = paymentCompletedEvent.getBookingId();
        PaymentBookingCompletedEvent paymentBookingCompletedEvent = buildPaymentBookingCompletedEventObjectFromPaymentCompletedEvent(paymentCompletedEvent);
        bookingEventProducer.producerPaymentBookingCompleted(paymentBookingCompletedEvent, sagaId);
        SagaBookingSnapshot sagaBookingSnapshot = getByBookingId(bookingId);
        FlightStartEvent flightStartEvent = buildFlightStartEventObjectFromSagaBookingSnapshot(sagaBookingSnapshot);
        flightEventProducer.producerFlightStart(flightStartEvent, sagaId);
    }

    @Override
    public void handlePaymentFailed(PaymentFailedEvent paymentFailedEvent, UUID sagaId) {
        PaymentBookingFailedEvent paymentBookingFailedEvent = buildPaymentBookingFailedEventObjectFromPaymentFailedEvent(paymentFailedEvent);
        bookingEventProducer.producerPaymentBookingFailed(paymentBookingFailedEvent, sagaId);
    }

    @Override
    public void handleFlightPaymentReversed(FlightPaymentReversedEvent flightPaymentReversedEvent, UUID sagaId) {
        FlightBookingFailedEvent flightBookingFailedEvent = buildFlightBookingFailedEventObjectFromFlightPaymentReversedEvent(flightPaymentReversedEvent);
        bookingEventProducer.producerFlightBookingFailed(flightBookingFailedEvent, sagaId);
    }

    @Override
    public void handleHotelPaymentReversed(HotelPaymentReversedEvent hotelPaymentReversedEvent, UUID sagaId) {
        HotelBookingFailedEvent hotelBookingFailedEvent = buildHotelBookingFailedEventObjectFromHotelPaymentReversedEvent(hotelPaymentReversedEvent);
        bookingEventProducer.producerHotelBookingFailed(hotelBookingFailedEvent, sagaId);
    }

    @Override
    public void handleFlightConfirmed(FlightConfirmedEvent flightConfirmedEvent, UUID sagaId) {
        final UUID bookingId = flightConfirmedEvent.getBookingId();
        FlightBookingConfirmedEvent flightBookingConfirmedEvent = buildFlightBookingConfirmedEventObjectFromFlightConfirmedEvent(flightConfirmedEvent);
        bookingEventProducer.producerFlightBookingConfirmed(flightBookingConfirmedEvent, sagaId);
        SagaBookingSnapshot sagaBookingSnapshot = getByBookingId(bookingId);
        HotelStartEvent hotelStartEvent = buildHotelStartEventObjectFromSagaBookingSnapshot(sagaBookingSnapshot);
        hotelEventProducer.producerHotelStart(hotelStartEvent, sagaId);
    }

    @Override
    public void handleFlightFailed(FlightFailedEvent flightFailedEvent, UUID sagaId) {
        FlightPaymentReverseStartEvent flightPaymentReverseStartEvent = buildFlightPaymentReverseStartEventObjectFromFlightFailedEvent(flightFailedEvent);
        paymentEventProducer.producerFlightPaymentReverseStart(flightPaymentReverseStartEvent, sagaId);
    }

    @Override
    public void handleHotelFailed(HotelFailedEvent hotelFailedEvent, UUID sagaId) {
        HotelFlightReverseStartEvent hotelFlightReverseStartEvent = buildHotelFlightReverseStartEventObjectFromHotelFailedEvent(hotelFailedEvent);
        flightEventProducer.producerHotelFlightReverseStart(hotelFlightReverseStartEvent, sagaId);
    }


    @Override
    public void handleHotelBooked(HotelBookedEvent hotelBookedEvent, UUID sagaId) {
        final UUID bookingId = hotelBookedEvent.getBookingId();
        HotelBookingBookedEvent hotelBookingBookedEvent = buildHotelBookingBookedEventObjectFromHotelBookedEvent(hotelBookedEvent);
        bookingEventProducer.producerHotelBookingBooked(hotelBookingBookedEvent, sagaId);
        SagaBookingSnapshot sagaBookingSnapshot = getByBookingId(bookingId);
        BookingConfirmedEvent bookingConfirmedEvent = buildBookingConfirmedEventObjectFromSagaBookingSnapshot(sagaBookingSnapshot);
        bookingEventProducer.producerBookingConfirmed(bookingConfirmedEvent, sagaId);
    }

    @Override
    public void handleHotelFlightReversed(HotelFlightReversedEvent hotelFlightReversedEvent, UUID sagaId) {
        HotelPaymentReverseStartEvent hotelPaymentReverseStartEvent = buildHotelPaymentReverseStartEventObjectFromHotelFlightReversedEvent(hotelFlightReversedEvent);
        paymentEventProducer.producerHotelPaymentReverseStart(hotelPaymentReverseStartEvent, sagaId);
    }

    @Override
    public SagaBookingSnapshot getByBookingId(UUID bookingId) {
        return sagaBookingSnapshotRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Saga Booking Snapshot Not Found"));
    }

    private HotelPaymentReverseStartEvent buildHotelPaymentReverseStartEventObjectFromHotelFlightReversedEvent(HotelFlightReversedEvent hotelFlightReversedEvent) {
        final UUID bookingId = hotelFlightReversedEvent.getBookingId();
        final String userId = hotelFlightReversedEvent.getUserId();
        final String paymentStatus = StepState.REVERSED.name();
        return HotelPaymentReverseStartEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .build();
    }

    private BookingConfirmedEvent buildBookingConfirmedEventObjectFromSagaBookingSnapshot(SagaBookingSnapshot sagaBookingSnapshot) {
        final UUID bookingId = sagaBookingSnapshot.getBookingId();
        final String bookingStatus = "CONFIRMED";
        return BookingConfirmedEvent.builder()
                .bookingId(bookingId)
                .bookingStatus(bookingStatus)
                .build();
    }

    private HotelBookingBookedEvent buildHotelBookingBookedEventObjectFromHotelBookedEvent(HotelBookedEvent hotelBookedEvent) {
        final UUID bookingId = hotelBookedEvent.getBookingId();
        final UUID hotelId = hotelBookedEvent.getHotelId();
        final String hotelStatus = hotelBookedEvent.getHotelStatus();
        return HotelBookingBookedEvent.builder()
                .bookingId(bookingId)
                .hotelId(hotelId)
                .hotelStatus(hotelStatus)
                .build();
    }

    private HotelStartEvent buildHotelStartEventObjectFromSagaBookingSnapshot(SagaBookingSnapshot sagaBookingSnapshot) {
        final UUID bookingId = sagaBookingSnapshot.getBookingId();
        final String userId = sagaBookingSnapshot.getUserId();
        final String hotelCode = sagaBookingSnapshot.getHotelCode();
        final LocalDate checkIn = sagaBookingSnapshot.getCheckIn();
        final LocalDate checkOut = sagaBookingSnapshot.getCheckOut();
        return HotelStartEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .hotelCode(hotelCode)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }

    private FlightStartEvent buildFlightStartEventObjectFromSagaBookingSnapshot(SagaBookingSnapshot sagaBookingSnapshot) {
        final UUID bookingId = sagaBookingSnapshot.getBookingId();
        final String userId = sagaBookingSnapshot.getUserId();
        final String flightFrom = sagaBookingSnapshot.getFlightFrom();
        final String flightTo = sagaBookingSnapshot.getFlightTo();
        final LocalDate flightDate = sagaBookingSnapshot.getFlightDate();
        final String flightNo = sagaBookingSnapshot.getFlightNo();
        return FlightStartEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .flightFrom(flightFrom)
                .flightTo(flightTo)
                .flightDate(flightDate)
                .flightNo(flightNo)
                .build();
    }

    private HotelBookingFailedEvent buildHotelBookingFailedEventObjectFromHotelPaymentReversedEvent(HotelPaymentReversedEvent hotelPaymentReversedEvent) {
        final UUID bookingId = hotelPaymentReversedEvent.getBookingId();
        final String userId = hotelPaymentReversedEvent.getUserId();
        final String bookingStatus = StepState.FAILED.name();
        return HotelBookingFailedEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .bookingStatus(bookingStatus)
                .build();
    }

    private FlightBookingFailedEvent buildFlightBookingFailedEventObjectFromFlightPaymentReversedEvent(FlightPaymentReversedEvent flightPaymentReversedEvent) {
        final UUID paymentId = flightPaymentReversedEvent.getPaymentId();
        final UUID bookingId = flightPaymentReversedEvent.getBookingId();
        final String userId = flightPaymentReversedEvent.getUserId();
        final String paymentStatus = flightPaymentReversedEvent.getPaymentStatus();
        final String flightStatus = flightPaymentReversedEvent.getFlightStatus();
        final String bookingStatus = flightPaymentReversedEvent.getBookingStatus();
        return FlightBookingFailedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .flightStatus(flightStatus)
                .bookingStatus(bookingStatus)
                .build();
    }

    private PaymentBookingFailedEvent buildPaymentBookingFailedEventObjectFromPaymentFailedEvent(PaymentFailedEvent paymentFailedEvent) {
        final UUID paymentId = paymentFailedEvent.getPaymentId();
        final UUID bookingId = paymentFailedEvent.getBookingId();
        final String paymentStatus = paymentFailedEvent.getPaymentStatus();
        final String bookingStatus = paymentFailedEvent.getPaymentStatus();
        return PaymentBookingFailedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .paymentStatus(paymentStatus)
                .bookingStatus(bookingStatus)
                .build();
    }

    private PaymentBookingCompletedEvent buildPaymentBookingCompletedEventObjectFromPaymentCompletedEvent(PaymentCompletedEvent paymentCompletedEvent) {
        final UUID paymentId = paymentCompletedEvent.getPaymentId();
        final UUID bookingId = paymentCompletedEvent.getBookingId();
        final String paymentStatus = paymentCompletedEvent.getPaymentStatus();
        return PaymentBookingCompletedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .paymentStatus(paymentStatus)
                .build();
    }

    private HotelFlightReverseStartEvent buildHotelFlightReverseStartEventObjectFromHotelFailedEvent(HotelFailedEvent hotelFailedEvent) {
        final UUID bookingId = hotelFailedEvent.getBookingId();
        final String userId = hotelFailedEvent.getUserId();
        final String flightStatus = StepState.REVERSED.name();
        return HotelFlightReverseStartEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .flightStatus(flightStatus)
                .build();
    }

    private FlightPaymentReverseStartEvent buildFlightPaymentReverseStartEventObjectFromFlightFailedEvent(FlightFailedEvent flightFailedEvent) {
        final UUID bookingId = flightFailedEvent.getBookingId();
        final String userId = flightFailedEvent.getUserId();
        final String paymentStatus = StepState.REVERSED.name();
        final String flightStatus = flightFailedEvent.getFlightStatus();
        final String bookingStatus = flightFailedEvent.getBookingStatus();
        return FlightPaymentReverseStartEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .flightStatus(flightStatus)
                .bookingStatus(bookingStatus)
                .build();
    }

    private FlightBookingConfirmedEvent buildFlightBookingConfirmedEventObjectFromFlightConfirmedEvent(FlightConfirmedEvent flightConfirmedEvent) {
        final UUID bookingId = flightConfirmedEvent.getBookingId();
        final UUID flightId = flightConfirmedEvent.getFlightId();
        final String flightStatus = flightConfirmedEvent.getFlightStatus();
        return FlightBookingConfirmedEvent.builder()
                .bookingId(bookingId)
                .flightId(flightId)
                .flightStatus(flightStatus)
                .build();
    }

    private SagaBookingSnapshot buildSagaBookingSnapshotObjectFromBookingCreatedEvent(BookingCreatedEvent bookingCreatedEvent, UUID sagaId) {
        final UUID bookingId = bookingCreatedEvent.getBookingId();
        final String userId = bookingCreatedEvent.getUserId();
        final BigDecimal amountTotal = bookingCreatedEvent.getAmountTotal();
        final String flightFrom = bookingCreatedEvent.getFlightFrom();
        final String flightTo = bookingCreatedEvent.getFlightTo();
        final LocalDate flightDate = bookingCreatedEvent.getFlightDate();
        final String flightNo = bookingCreatedEvent.getFlightNo();
        final String hotelCode = bookingCreatedEvent.getHotelCode();
        final LocalDate checkIn = bookingCreatedEvent.getCheckIn();
        final LocalDate checkOut = bookingCreatedEvent.getCheckOut();
        return SagaBookingSnapshot.builder()
                .bookingId(bookingId)
                .sagaId(sagaId)
                .userId(userId)
                .amountTotal(amountTotal)
                .flightFrom(flightFrom)
                .flightTo(flightTo)
                .flightDate(flightDate)
                .flightNo(flightNo)
                .hotelCode(hotelCode)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }

    private PaymentStartEvent buildPaymentStartEventObjectFromSagaBookingSnapshot(SagaBookingSnapshot sagaBookingSnapshot) {
        final UUID bookingId = sagaBookingSnapshot.getBookingId();
        final String userId = sagaBookingSnapshot.getUserId();
        final BigDecimal amountTotal = sagaBookingSnapshot.getAmountTotal();
        return PaymentStartEvent.builder()
                .bookingId(bookingId)
                .userId(userId)
                .amountTotal(amountTotal)
                .build();
    }
}
