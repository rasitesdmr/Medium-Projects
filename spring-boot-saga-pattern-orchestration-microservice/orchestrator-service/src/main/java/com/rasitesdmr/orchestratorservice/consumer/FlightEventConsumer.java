package com.rasitesdmr.orchestratorservice.consumer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.OrchestratorService;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.FlightConfirmedEvent;
import kafka.event.FlightFailedEvent;
import kafka.event.FlightPaymentReversedEvent;
import kafka.event.HotelFlightReversedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FlightEventConsumer {

    private final static Logger log = LoggerFactory.getLogger(FlightEventConsumer.class);

    private final OrchestratorService orchestratorService;
    private final SagaStepService sagaStepService;

    public FlightEventConsumer(OrchestratorService orchestratorService, SagaStepService sagaStepService) {
        this.orchestratorService = orchestratorService;
        this.sagaStepService = sagaStepService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_CONFIRMED,
            groupId = "orchestrator-service")
    public void consumerFlightConfirmed(@Payload FlightConfirmedEvent flightConfirmedEvent,
                                        @Header(name = "saga_id", required = false) UUID sagaId,
                                        Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Confirmed, CONSUMER EVENT: flight-confirmed", sagaId, flightConfirmedEvent.getBookingId());
        sagaStepService.createSagaStep(flightConfirmedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_CONFIRMED, "Flight Confirmed");
        orchestratorService.handleFlightConfirmed(flightConfirmedEvent,sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_FAILED,
            groupId = "orchestrator-service")
    public void consumerFlightFailed(@Payload FlightFailedEvent flightFailedEvent,
                                        @Header(name = "saga_id", required = false) UUID sagaId,
                                        Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Failed, CONSUMER EVENT: flight-failed", sagaId, flightFailedEvent.getBookingId());
        sagaStepService.createSagaStep(flightFailedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_FAILED, "Flight Failed");
        orchestratorService.handleFlightFailed(flightFailedEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_FLIGHT_REVERSED,
            groupId = "orchestrator-service")
    public void consumerHotelFlightReversed(@Payload HotelFlightReversedEvent hotelFlightReversedEvent,
                                            @Header(name = "saga_id", required = false) UUID sagaId,
                                            Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Flight Reversed , CONSUMER EVENT: hotel-flight-reversed", sagaId, hotelFlightReversedEvent.getBookingId());
        sagaStepService.createSagaStep(hotelFlightReversedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_FLIGHT_REVERSED, "Hotel Flight Reversed");
        orchestratorService.handleHotelFlightReversed(hotelFlightReversedEvent, sagaId);
        ack.acknowledge();
    }

}
