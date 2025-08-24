package com.rasitesdmr.orchestratorservice.repository;

import com.rasitesdmr.orchestratorservice.model.SagaStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SagaStepRepository extends JpaRepository<SagaStep, UUID> {
}
