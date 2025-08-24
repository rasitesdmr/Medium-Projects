package com.rasitesdmr.hotelservice.producer;

import com.rasitesdmr.hotelservice.topic.KafkaTopic;
import kafka.event.HotelBookedEvent;
import kafka.event.HotelFailedEvent;
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

    public HotelEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void producerHotelBooked(HotelBookedEvent hotelBookedEvent, UUID sagaId) {
        Message<HotelBookedEvent> event = MessageBuilder
                .withPayload(hotelBookedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_BOOKED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }

    public void producerHotelFailed(HotelFailedEvent hotelFailedEvent, UUID sagaId) {
        Message<HotelFailedEvent> event = MessageBuilder
                .withPayload(hotelFailedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_FAILED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }
}
