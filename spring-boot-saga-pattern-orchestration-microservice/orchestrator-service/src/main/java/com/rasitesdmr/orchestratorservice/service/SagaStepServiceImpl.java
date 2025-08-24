package com.rasitesdmr.orchestratorservice.service;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import com.rasitesdmr.orchestratorservice.model.SagaStep;
import com.rasitesdmr.orchestratorservice.repository.SagaStepRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SagaStepServiceImpl implements SagaStepService {

    private final Logger log = LoggerFactory.getLogger(SagaStepServiceImpl.class);
    private final SagaStepRepository sagaStepRepository;

    public SagaStepServiceImpl(SagaStepRepository sagaStepRepository) {
        this.sagaStepRepository = sagaStepRepository;
    }


    @Override
    public void saveSagaStep(SagaStep sagaStep) {
        try {
            sagaStepRepository.save(sagaStep);
        } catch (Exception exception) {
            log.error("Saga step save error message : {}", exception.getMessage());
        }
    }

    @Override
    public void createSagaStep(UUID bookingId, UUID sagaId, SagaStepEventType sagaStepEventType, String logMessage) {
        SagaStep sagaStep = SagaStep.builder()
                .bookingId(bookingId)
                .sagaId(sagaId)
                .sagaStepEventType(sagaStepEventType)
                .logMessage(logMessage)
                .build();

        saveSagaStep(sagaStep);
    }
}
