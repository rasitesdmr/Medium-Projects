package com.rasitesdmr.bookingservice.consumer;

import com.rasitesdmr.bookingservice.service.BookingService;
import com.rasitesdmr.bookingservice.topic.KafkaTopic;
import kafka.event.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingEventConsumer {

    private final BookingService bookingService;

    public BookingEventConsumer(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_BOOKING_CONFIRMED, groupId = "orchestrator-service")
    public void consumerBookingConfirmed(@Payload BookingConfirmedEvent bookingConfirmedEvent,
                                         @Header(name = "saga_id", required = false) UUID sagaId,
                                         Acknowledgment ack) {

        bookingService.updateBookingConfirmedStatus(bookingConfirmedEvent);
        ack.acknowledge();
    }

}
