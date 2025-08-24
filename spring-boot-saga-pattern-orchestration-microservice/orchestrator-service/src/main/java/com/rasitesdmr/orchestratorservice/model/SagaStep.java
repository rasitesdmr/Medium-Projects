package com.rasitesdmr.orchestratorservice.model;

import com.rasitesdmr.orchestratorservice.enums.SagaStepEventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "saga_steps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SagaStep {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;
    private UUID sagaId;
    private UUID bookingId;

    @Enumerated(EnumType.STRING)
    private SagaStepEventType sagaStepEventType;

    private String logMessage;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
