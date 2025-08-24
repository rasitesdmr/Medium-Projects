package com.rasitesdmr.orchestratorservice.repository;

import com.rasitesdmr.orchestratorservice.model.SagaBookingSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SagaBookingSnapshotRepository extends JpaRepository<SagaBookingSnapshot, UUID> {
}
