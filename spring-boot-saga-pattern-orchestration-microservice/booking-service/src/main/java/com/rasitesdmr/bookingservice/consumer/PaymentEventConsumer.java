package com.rasitesdmr.bookingservice.consumer;

import com.rasitesdmr.bookingservice.service.BookingService;
import com.rasitesdmr.bookingservice.topic.KafkaTopic;
import kafka.event.PaymentBookingCompletedEvent;
import kafka.event.PaymentBookingFailedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentEventConsumer {

    private final BookingService bookingService;

    public PaymentEventConsumer(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_PAYMENT_BOOKING_COMPLETED, groupId = "orchestrator-service")
    public void consumerPaymentBookingCompleted(@Payload PaymentBookingCompletedEvent paymentBookingCompletedEvent,
                                                @Header(name = "saga_id", required = false) UUID sagaId,
                                                Acknowledgment ack) {
        bookingService.updatePaymentBookingCompletedStatus(paymentBookingCompletedEvent);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_PAYMENT_BOOKING_FAILED, groupId = "orchestrator-service")
    public void consumerPaymentBookingFailed(@Payload PaymentBookingFailedEvent paymentBookingFailedEvent,
                                             @Header(name = "saga_id", required = false) UUID sagaId,
                                             Acknowledgment ack) {
        bookingService.updatePaymentBookingFailedStatus(paymentBookingFailedEvent);
        ack.acknowledge();
    }
}
