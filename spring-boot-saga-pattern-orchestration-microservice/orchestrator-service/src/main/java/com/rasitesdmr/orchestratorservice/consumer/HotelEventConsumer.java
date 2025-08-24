package com.rasitesdmr.orchestratorservice.consumer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.OrchestratorService;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.FlightConfirmedEvent;
import kafka.event.FlightFailedEvent;
import kafka.event.HotelBookedEvent;
import kafka.event.HotelFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HotelEventConsumer {

    private final static Logger log = LoggerFactory.getLogger(HotelEventConsumer.class);

    private final OrchestratorService orchestratorService;
    private final SagaStepService sagaStepService;

    public HotelEventConsumer(OrchestratorService orchestratorService, SagaStepService sagaStepService) {
        this.orchestratorService = orchestratorService;
        this.sagaStepService = sagaStepService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_BOOKED,
            groupId = "orchestrator-service")
    public void consumerHotelBooked(@Payload HotelBookedEvent hotelBookedEvent,
                                    @Header(name = "saga_id", required = false) UUID sagaId,
                                    Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Booked, CONSUMER EVENT: hotel-booked", sagaId, hotelBookedEvent.getBookingId());
        sagaStepService.createSagaStep(hotelBookedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_BOOKED, "Hotel Booked");
        orchestratorService.handleHotelBooked(hotelBookedEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_FAILED,
            groupId = "orchestrator-service")
    public void consumerHotelFailed(@Payload HotelFailedEvent hotelFailedEvent,
                                    @Header(name = "saga_id", required = false) UUID sagaId,
                                    Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Failed, CONSUMER EVENT: hotel-failed", sagaId, hotelFailedEvent.getBookingId());
        sagaStepService.createSagaStep(hotelFailedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_FAILED, "Hotel Failed");
        orchestratorService.handleHotelFailed(hotelFailedEvent, sagaId);
        ack.acknowledge();
    }
}
