package com.rasitesdmr.stock_service.consumer;

import com.rasitesdmr.stock_service.domain.enums.OrderStatus;
import com.rasitesdmr.stock_service.stock.service.StockService;
import event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OrderCreatedConsumer {

    private final StockService stockService;

    public OrderCreatedConsumer(StockService stockService) {
        this.stockService = stockService;
    }

    @KafkaListener(topics = "order-created-topic", containerFactory = "kafkaListenerContainerFactory")
    public void consumerOrderCreatedEvent(@Payload OrderCreatedEvent orderCreatedEvent, Acknowledgment ack) throws InterruptedException {
        Thread.sleep(Duration.ofMinutes(1));
        stockService.decreaseStockQuantity(orderCreatedEvent);
        ack.acknowledge();
    }


    @KafkaListener(topics = "order-created-error-topic", containerFactory = "kafkaListenerContainerFactory")
    public void consumerOrderCreatedErrorEvent(@Payload OrderCreatedEvent orderCreatedEvent, Acknowledgment ack) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(30));
        stockService.decreaseStockQuantity(orderCreatedEvent);
        ack.acknowledge();
    }


    @KafkaListener(topics = "order-created-dlq-topic", containerFactory = "kafkaListenerContainerFactory")
    public void consumerOrderCreatedDLQEvent(@Payload OrderCreatedEvent orderCreatedEvent, Acknowledgment ack) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(30));
        stockService.updateOrderConfirmationStatus(orderCreatedEvent.getOrderId(), OrderStatus.FAILED.name());
        ack.acknowledge();
    }
}
