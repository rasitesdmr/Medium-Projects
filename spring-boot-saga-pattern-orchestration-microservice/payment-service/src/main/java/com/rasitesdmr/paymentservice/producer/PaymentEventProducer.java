package com.rasitesdmr.paymentservice.producer;

import com.rasitesdmr.paymentservice.topic.KafkaTopic;
import kafka.event.HotelPaymentReversedEvent;
import kafka.event.PaymentCompletedEvent;
import kafka.event.PaymentFailedEvent;
import kafka.event.FlightPaymentReversedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentEventProducer {

    private final static Logger log = LoggerFactory.getLogger(PaymentEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void producerPaymentCompleted(PaymentCompletedEvent paymentCompletedEvent, UUID sagaId) {
        Message<PaymentCompletedEvent> event = MessageBuilder
                .withPayload(paymentCompletedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_PAYMENT_COMPLETED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }

    public void producerPaymentFailed(PaymentFailedEvent paymentFailedEvent, UUID sagaId) {
        Message<PaymentFailedEvent> event = MessageBuilder
                .withPayload(paymentFailedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_PAYMENT_FAILED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }

    public void producerFlightPaymentReversed(FlightPaymentReversedEvent flightPaymentReversedEvent, UUID sagaId) {
        Message<FlightPaymentReversedEvent> event = MessageBuilder
                .withPayload(flightPaymentReversedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_FLIGHT_PAYMENT_REVERSED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }

    public void producerHotelPaymentReversed(HotelPaymentReversedEvent hotelPaymentReversedEvent, UUID sagaId) {
        Message<HotelPaymentReversedEvent> event = MessageBuilder
                .withPayload(hotelPaymentReversedEvent)
                .setHeader(KafkaHeaders.TOPIC, KafkaTopic.TOPIC_HOTEL_PAYMENT_REVERSED)
                .setHeader("saga_id", sagaId)
                .build();
        kafkaTemplate.send(event);
    }


}
