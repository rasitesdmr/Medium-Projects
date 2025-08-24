package com.rasitesdmr.orchestratorservice.consumer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.OrchestratorService;
import com.rasitesdmr.orchestratorservice.service.OrchestratorServiceImpl;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import kafka.event.BookingCreatedEvent;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class BookingEventConsumer {
    private final static Logger log = LoggerFactory.getLogger(BookingEventConsumer.class);

    private final OrchestratorService orchestratorService;
    private final SagaStepService sagaStepService;

    public BookingEventConsumer(OrchestratorService orchestratorService, SagaStepService sagaStepService) {
        this.orchestratorService = orchestratorService;
        this.sagaStepService = sagaStepService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_BOOKING_CREATED,
            groupId = "orchestrator-service")
    public void consumerBookingCreated(@Payload BookingCreatedEvent bookingCreatedEvent,
                                       @Header(name = "saga_id", required = false) UUID sagaId,
                                       Acknowledgment ack) {
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Booking Created , CONSUMER EVENT: booking-created", sagaId, bookingCreatedEvent.getBookingId());
        sagaStepService.createSagaStep(bookingCreatedEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_BOOKING_CREATED, "Booking Created");
        orchestratorService.handleBookingCreated(bookingCreatedEvent, sagaId);
        ack.acknowledge();
    }
}
