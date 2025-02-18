package com.rasitesdmr.order_service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.outbox.Outbox;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OutboxMapper {

    @SneakyThrows
    public Outbox outboxToOrder(Order order){
        return Outbox.builder()
                .aggregateId(order.getId().toString())
                .payload(new ObjectMapper().writeValueAsString(order))
                .createdAt(new Date())
                .processed(false)
                .build();
    }
}
