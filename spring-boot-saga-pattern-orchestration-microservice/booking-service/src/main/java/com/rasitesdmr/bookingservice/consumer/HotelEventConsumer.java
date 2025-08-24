package com.rasitesdmr.bookingservice.consumer;

import com.rasitesdmr.bookingservice.service.BookingService;
import com.rasitesdmr.bookingservice.topic.KafkaTopic;
import kafka.event.HotelBookingBookedEvent;
import kafka.event.HotelBookingFailedEvent;
import kafka.event.PaymentBookingFailedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HotelEventConsumer {

    private final BookingService bookingService;

    public HotelEventConsumer(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_BOOKING_BOOKED, groupId = "orchestrator-service")
    public void consumerHotelBookingBooked(@Payload HotelBookingBookedEvent hotelBookingBookedEvent,
                                           @Header(name = "saga_id", required = false) UUID sagaId,
                                           Acknowledgment ack) {
        bookingService.updateHotelBookingBookedStatus(hotelBookingBookedEvent);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_BOOKING_FAILED, groupId = "orchestrator-service")
    public void consumerHotelBookingFailed(@Payload HotelBookingFailedEvent hotelBookingFailedEvent,
                                           @Header(name = "saga_id", required = false) UUID sagaId,
                                           Acknowledgment ack) {
        bookingService.updateHotelBookingFailedStatus(hotelBookingFailedEvent);
        ack.acknowledge();
    }
}
