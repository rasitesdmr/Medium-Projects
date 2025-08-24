package com.rasitesdmr.orchestratorservice.service;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.model.SagaStep;

import java.util.UUID;

public interface SagaStepService {
    void saveSagaStep(SagaStep sagaStep);

    void createSagaStep(UUID bookingId, UUID sagaId, SagaStepEventType sagaStepEventType, String logMessage);
}
