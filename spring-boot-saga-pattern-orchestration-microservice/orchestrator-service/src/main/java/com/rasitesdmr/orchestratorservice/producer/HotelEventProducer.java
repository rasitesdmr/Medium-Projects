package com.rasitesdmr.orchestratorservice.producer;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.service.SagaStepService;
import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import kafka.event.FlightStartEvent;
import kafka.event.HotelStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HotelEventProducer {

    private final static Logger log = LoggerFactory.getLogger(HotelEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SagaStepService sagaStepService;

    public HotelEventProducer(KafkaTemplate<String, Object> kafkaTemplate, SagaStepService sagaStepService) {
        this.kafkaTemplate = kafkaTemplate;
        this.sagaStepService = sagaStepService;
    }

    public void producerHotelStart(HotelStartEvent hotelStartEvent, UUID sagaId) {
        Message<HotelStartEvent> event = MessageBuilder
                .withPayload(hotelStartEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_START)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
        log.info("[Saga ID: {}] - [Booking ID: {}] - STEP: Hotel Start , PRODUCER EVENT: hotel-start", sagaId, hotelStartEvent.getBookingId());
        sagaStepService.createSagaStep(hotelStartEvent.getBookingId(), sagaId, SagaStepEventType.TOPIC_HOTEL_START, "Hotel Start");
    }
}
