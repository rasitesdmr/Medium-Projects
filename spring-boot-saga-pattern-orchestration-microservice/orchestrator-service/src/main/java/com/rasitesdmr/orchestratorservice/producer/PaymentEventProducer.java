package com.rasitesdmr.orchestratorservice.producer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.FlightPaymentReverseStartEvent;
import kafka.event.HotelPaymentReverseStartEvent;
import kafka.event.PaymentStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentEventProducer {
    private final static Logger log = LoggerFactory.getLogger(PaymentEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaStepService sagaStepService;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate, SagaStepService sagaStepService) {
        this.kafkaTemplate = kafkaTemplate;
        this.sagaStepService = sagaStepService;
    }

    public void producerPaymentStart(PaymentStartEvent paymentStartEvent, UUID sagaId) {
        Message<PaymentStartEvent> event = MessageBuilder
                .withPayload(paymentStartEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_PAYMENT_START)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Payment Start , PRODUCER EVENT: payment-start", sagaId, paymentStartEvent.getBookingId());
        sagaStepService.createSagaStep(paymentStartEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_PAYMENT_START, "Payment Start");
    }

    public void producerFlightPaymentReverseStart(FlightPaymentReverseStartEvent flightPaymentReverseStartEvent, UUID sagaId) {
        Message<FlightPaymentReverseStartEvent> event = MessageBuilder
                .withPayload(flightPaymentReverseStartEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_PAYMENT_REVERSE_START)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Payment Reverse Start, PRODUCER EVENT: flight-payment-reverse-start", sagaId, flightPaymentReverseStartEvent.getBookingId());
        sagaStepService.createSagaStep(flightPaymentReverseStartEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_PAYMENT_REVERSE_START, "Flight Payment Reverse Start");

    }

    public void producerHotelPaymentReverseStart(HotelPaymentReverseStartEvent hotelPaymentReverseStartEvent, UUID sagaId) {
        Message<HotelPaymentReverseStartEvent> event = MessageBuilder
                .withPayload(hotelPaymentReverseStartEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_PAYMENT_REVERSE_START)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Payment Reverse Start, PRODUCER EVENT: hotel-payment-reverse-start", sagaId, hotelPaymentReverseStartEvent.getBookingId());
        sagaStepService.createSagaStep(hotelPaymentReverseStartEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_PAYMENT_REVERSE_START, "Hotel Payment Reverse Start");
    }


}
