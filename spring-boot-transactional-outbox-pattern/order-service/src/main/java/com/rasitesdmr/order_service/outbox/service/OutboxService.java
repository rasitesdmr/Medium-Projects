package com.rasitesdmr.order_service.outbox.service;

import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.outbox.Outbox;

public interface OutboxService {

    void createOutbox(Order order);
}
