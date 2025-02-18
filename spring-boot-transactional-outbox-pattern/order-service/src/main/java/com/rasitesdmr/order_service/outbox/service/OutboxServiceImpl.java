package com.rasitesdmr.order_service.outbox.service;

import com.rasitesdmr.order_service.mapper.OutboxMapper;
import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.outbox.Outbox;
import com.rasitesdmr.order_service.outbox.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;
    private final OutboxMapper outboxMapper;

    public OutboxServiceImpl(OutboxRepository outboxRepository, OutboxMapper outboxMapper) {
        this.outboxRepository = outboxRepository;
        this.outboxMapper = outboxMapper;
    }

    @Override
    public void createOutbox(Order order) {
        Outbox outbox = outboxMapper.outboxToOrder(order);
        outboxRepository.save(outbox);
    }
}
