package com.rasitesdmr.orchestratorservice.producer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingEventProducer {

    private final static Logger log = LoggerFactory.getLogger(BookingEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaStepService sagaStepService;

    public BookingEventProducer(KafkaTemplate<String, Object> kafkaTemplate, SagaStepService sagaStepService) {
        this.kafkaTemplate = kafkaTemplate;
        this.sagaStepService = sagaStepService;
    }

    public void producerPaymentBookingCompleted(PaymentBookingCompletedEvent paymentBookingCompletedEvent, UUID sagaId) {
        Message<PaymentBookingCompletedEvent> event = MessageBuilder
                .withPayload(paymentBookingCompletedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_PAYMENT_BOOKING_COMPLETED)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Payment Booking Completed , PRODUCER EVENT: payment-booking-completed", sagaId, paymentBookingCompletedEvent.getBookingId());
        sagaStepService.createSagaStep(paymentBookingCompletedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_PAYMENT_BOOKING_COMPLETED, "Payment Booking Completed");
    }

    public void producerFlightBookingConfirmed(FlightBookingConfirmedEvent flightBookingConfirmedEvent, UUID sagaId) {
        Message<FlightBookingConfirmedEvent> event = MessageBuilder
                .withPayload(flightBookingConfirmedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_BOOKING_CONFIRMED)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Booking Confirmed , PRODUCER EVENT: flight-booking-confirmed", sagaId, flightBookingConfirmedEvent.getBookingId());
        sagaStepService.createSagaStep(flightBookingConfirmedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_BOOKING_CONFIRMED, "Flight Booking Confirmed");
    }

    public void producerHotelBookingBooked(HotelBookingBookedEvent hotelBookingBookedEvent, UUID sagaId) {
        Message<HotelBookingBookedEvent> event = MessageBuilder
                .withPayload(hotelBookingBookedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_BOOKING_BOOKED)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Booking Booked , PRODUCER EVENT: hotel-booking-booked", sagaId, hotelBookingBookedEvent.getBookingId());
        sagaStepService.createSagaStep(hotelBookingBookedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_BOOKING_BOOKED, "Hotel Booking Booked");
    }

    public void producerBookingConfirmed(BookingConfirmedEvent bookingConfirmedEvent, UUID sagaId) {
        Message<BookingConfirmedEvent> event = MessageBuilder
                .withPayload(bookingConfirmedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_BOOKING_CONFIRMED)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Booking Confirmed , PRODUCER EVENT: booking-confirmed", sagaId, bookingConfirmedEvent.getBookingId());
        sagaStepService.createSagaStep(bookingConfirmedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_BOOKING_CONFIRMED, "Booking Confirmed");
    }


    public void producerPaymentBookingFailed(PaymentBookingFailedEvent paymentBookingFailedEvent, UUID sagaId) {
        Message<PaymentBookingFailedEvent> event = MessageBuilder
                .withPayload(paymentBookingFailedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_PAYMENT_BOOKING_FAILED)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Payment Booking Failed, PRODUCER EVENT: payment-booking-failed", sagaId, paymentBookingFailedEvent.getBookingId());
        sagaStepService.createSagaStep(paymentBookingFailedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_PAYMENT_BOOKING_FAILED, "Payment Booking Failed");
    }

    public void producerFlightBookingFailed(FlightBookingFailedEvent flightBookingFailedEvent, UUID sagaId) {
        Message<FlightBookingFailedEvent> event = MessageBuilder
                .withPayload(flightBookingFailedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_BOOKING_FAILED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Booking Failed, PRODUCER EVENT: flight-booking-failed", sagaId, flightBookingFailedEvent.getBookingId());
        sagaStepService.createSagaStep(flightBookingFailedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_BOOKING_FAILED, "Flight Booking Failed");
    }

    public void producerHotelBookingFailed(HotelBookingFailedEvent hotelBookingFailedEvent, UUID sagaId) {
        Message<HotelBookingFailedEvent> event = MessageBuilder
                .withPayload(hotelBookingFailedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_BOOKING_FAILED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Booking Failed, PRODUCER EVENT: hotel-booking-failed", sagaId, hotelBookingFailedEvent.getBookingId());
        sagaStepService.createSagaStep(hotelBookingFailedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_BOOKING_FAILED, "Hotel Booking Failed");
    }
}
