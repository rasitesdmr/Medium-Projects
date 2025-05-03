package com.rasitesdmr.order_service.producer;

import com.rasitesdmr.order_service.config.KafkaTopicConfig;
import event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void producerOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        kafkaTemplate.send(KafkaTopicConfig.ORDER_CREATED_TOPIC, orderCreatedEvent);
    }
}
