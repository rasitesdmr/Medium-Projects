package com.rasitesdmr.flightservice.producer;

import com.rasitesdmr.flightservice.topic.KafkaTopic;
import kafka.event.FlightConfirmedEvent;
import kafka.event.FlightFailedEvent;
import kafka.event.HotelFlightReversedEvent;
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

    public FlightEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void producerFlightConfirmed(FlightConfirmedEvent flightConfirmedEvent, UUID sagaId) {
        Message<FlightConfirmedEvent> event = MessageBuilder
                .withPayload(flightConfirmedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_CONFIRMED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }

    public void producerFlightFailed(FlightFailedEvent flightFailedEvent, UUID sagaId) {
        Message<FlightFailedEvent> event = MessageBuilder
                .withPayload(flightFailedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_FAILED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }

    public void producerHotelFlightReversedEvent(HotelFlightReversedEvent hotelFlightReversedEvent, UUID sagaId) {
        Message<HotelFlightReversedEvent> event = MessageBuilder
                .withPayload(hotelFlightReversedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_FLIGHT_REVERSED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }
}
