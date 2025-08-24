package com.rasitesdmr.paymentservice.consumer;

import com.rasitesdmr.paymentservice.service.PaymentService;
import com.rasitesdmr.paymentservice.topic.KafkaTopic;
import kafka.event.FlightPaymentReverseStartEvent;
import kafka.event.HotelPaymentReverseStartEvent;
import kafka.event.PaymentStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentEventConsumer {

    private final static Logger log = LoggerFactory.getLogger(PaymentEventConsumer.class);
    private final PaymentService paymentService;

    public PaymentEventConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_PAYMENT_START,
            groupId = "orchestrator-service")
    public void consumerPaymentStart(@Payload PaymentStartEvent paymentStartEvent,
                                     @Header(name = "saga_id", required = false) UUID sagaId,
                                     Acknowledgment ack) {
        paymentService.createPayment(paymentStartEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_PAYMENT_REVERSE_START,
            groupId = "orchestrator-service")
    public void consumerFlightPaymentReverseStart(@Payload FlightPaymentReverseStartEvent flightPaymentReverseStartEvent,
                                            @Header(name = "saga_id", required = false) UUID sagaId,
                                            Acknowledgment ack) {
        paymentService.handleFlightPaymentReverseStart(flightPaymentReverseStartEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_PAYMENT_REVERSE_START,
            groupId = "orchestrator-service")
    public void consumerHotelPaymentReverseStart(@Payload HotelPaymentReverseStartEvent hotelPaymentReverseStartEvent,
                                                 @Header(name = "saga_id", required = false) UUID sagaId,
                                                 Acknowledgment ack) {
        paymentService.handleHotelPaymentReverseStart(hotelPaymentReverseStartEvent, sagaId);
        ack.acknowledge();
    }


}
