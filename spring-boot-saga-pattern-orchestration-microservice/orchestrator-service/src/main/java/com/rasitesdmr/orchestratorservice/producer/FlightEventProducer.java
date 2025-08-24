package com.rasitesdmr.orchestratorservice.producer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.FlightPaymentReverseStartEvent;
import kafka.event.FlightStartEvent;
import kafka.event.HotelFlightReverseStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FlightEventProducer {

    private final static Logger log = LoggerFactory.getLogger(FlightEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaStepService sagaStepService;

    public FlightEventProducer(KafkaTemplate<String, Object> kafkaTemplate, SagaStepService sagaStepService) {
        this.kafkaTemplate = kafkaTemplate;
        this.sagaStepService = sagaStepService;
    }

    public void producerFlightStart(FlightStartEvent flightStartEvent, UUID sagaId) {
        Message<FlightStartEvent> event = MessageBuilder
                .withPayload(flightStartEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_START)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Flight Start , PRODUCER EVENT: flight-start", sagaId, flightStartEvent.getBookingId());
        sagaStepService.createSagaStep(flightStartEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_FLIGHT_START, "Flight Start");
    }

    public void producerHotelFlightReverseStart(HotelFlightReverseStartEvent hotelFlightReverseStartEvent, UUID sagaId) {
        Message<HotelFlightReverseStartEvent> event = MessageBuilder
                .withPayload(hotelFlightReverseStartEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_FLIGHT_REVERSE_START)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Flight Reverse Start, PRODUCER EVENT: hotel-flight-reverse-start", sagaId, hotelFlightReverseStartEvent.getBookingId());
        sagaStepService.createSagaStep(hotelFlightReverseStartEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_FLIGHT_REVERSE_START, "Hotel Flight Reverse Start");

    }
}
