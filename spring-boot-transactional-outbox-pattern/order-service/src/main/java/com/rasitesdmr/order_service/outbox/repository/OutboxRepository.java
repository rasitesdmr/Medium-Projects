package com.rasitesdmr.order_service.outbox.repository;

import com.rasitesdmr.order_service.outbox.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
}
