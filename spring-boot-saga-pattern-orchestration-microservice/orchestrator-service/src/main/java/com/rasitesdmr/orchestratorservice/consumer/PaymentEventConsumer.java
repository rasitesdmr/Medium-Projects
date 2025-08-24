package com.rasitesdmr.orchestratorservice.consumer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.OrchestratorService;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.HotelPaymentReversedEvent;
import kafka.event.PaymentCompletedEvent;
import kafka.event.PaymentFailedEvent;
import kafka.event.FlightPaymentReversedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentEventConsumer {

    private final static Logger log = LoggerFactory.getLogger(PaymentEventConsumer.class);

    private final OrchestratorService orchestratorService;
    private final SagaStepService sagaStepService;

    public PaymentEventConsumer(OrchestratorService orchestratorService, SagaStepService sagaStepService) {
        this.orchestratorService = orchestratorService;
        this.sagaStepService = sagaStepService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_PAYMENT_COMPLETED,
            groupId = "orchestrator-service")
    public void consumerPaymentCompleted(@Payload PaymentCompletedEvent paymentCompletedEvent,
                                         @Header(name = "saga_id", required = false) UUID sagaId,
                                         Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Payment Completed , CONSUMER EVENT: payment-completed", sagaId, paymentCompletedEvent.getBookingId());
        sagaStepService.createSagaStep(paymentCompletedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_PAYMENT_COMPLETED, "Payment Completed");
        orchestratorService.handlePaymentCompleted(paymentCompletedEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_PAYMENT_FAILED,
            groupId = "orchestrator-service")
    public void consumerPaymentFailed(@Payload PaymentFailedEvent paymentFailedEvent,
                                      @Header(name = "saga_id", required = false) UUID sagaId,
                                      Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Payment Failed , CONSUMER EVENT: payment-failed", sagaId, paymentFailedEvent.getBookingId());
        sagaStepService.createSagaStep(paymentFailedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_PAYMENT_FAILED, "Payment Failed");
        orchestratorService.handlePaymentFailed(paymentFailedEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_PAYMENT_REVERSED,
            groupId = "orchestrator-service")
    public void consumerFlightPaymentReversed(@Payload FlightPaymentReversedEvent flightPaymentReversedEvent,
                                              @Header(name = "saga_id", required = false) UUID sagaId,
                                              Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Payment Reversed , CONSUMER EVENT: flight-payment-reversed", sagaId, flightPaymentReversedEvent.getBookingId());
        sagaStepService.createSagaStep(flightPaymentReversedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_PAYMENT_REVERSED, "Flight Payment Reversed");
        orchestratorService.handleFlightPaymentReversed(flightPaymentReversedEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_PAYMENT_REVERSED,
            groupId = "orchestrator-service")
    public void consumerHotelPaymentReversed(@Payload HotelPaymentReversedEvent hotelPaymentReversedEvent,
                                             @Header(name = "saga_id", required = false) UUID sagaId,
                                             Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Payment Reversed , CONSUMER EVENT: hotel-payment-reversed", sagaId, hotelPaymentReversedEvent.getBookingId());
        sagaStepService.createSagaStep(hotelPaymentReversedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_PAYMENT_REVERSED, "Hotel Payment Reversed");
        orchestratorService.handleHotelPaymentReversed(hotelPaymentReversedEvent, sagaId);
        ack.acknowledge();
    }
}
