package com.rasitesdmr.bookingservice.producer;

import kafka.event.BookingCreatedEvent;
import com.rasitesdmr.bookingservice.topic.KafkaTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@Component
public class BookingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookingEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void producerBookingCreated(BookingCreatedEvent bookingCreatedEvent, UUID sagaId) {
        Message<BookingCreatedEvent> event = MessageBuilder
                .withPayload(bookingCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_BOOKING_CREATED)
                .setHeader("saga_id", sagaId)
                .build();

        kafkaTemplate.send(event);
    }
}
