package com.rasitesdmr.bookingservice.consumer;

import com.rasitesdmr.bookingservice.service.BookingService;
import com.rasitesdmr.bookingservice.topic.KafkaTopic;
import kafka.event.FlightBookingFailedEvent;
import kafka.event.FlightBookingConfirmedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FlightEventConsumer {

    private final BookingService bookingService;

    public FlightEventConsumer(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_BOOKING_CONFIRMED, groupId = "orchestrator-service")
    public void consumerFlightBookingConfirmed(@Payload FlightBookingConfirmedEvent flightBookingConfirmedEvent,
                                               @Header(name = "saga_id", required = false) UUID sagaId,
                                               Acknowledgment ack) {
        bookingService.updateFlightBookingConfirmedStatus(flightBookingConfirmedEvent);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_BOOKING_FAILED, groupId = "orchestrator-service")
    public void consumerFlightBookingFailed(@Payload FlightBookingFailedEvent flightBookingFailedEvent,
                                            @Header(name = "saga_id", required = false) UUID sagaId,
                                            Acknowledgment ack) {
        bookingService.updateFlightBookingFailedStatus(flightBookingFailedEvent);
        ack.acknowledge();
    }
}
