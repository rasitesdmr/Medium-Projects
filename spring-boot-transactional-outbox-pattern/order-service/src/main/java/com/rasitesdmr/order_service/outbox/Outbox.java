package com.rasitesdmr.order_service.outbox;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "outboxs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String aggregateId;
    private String payload;
    private Date createdAt;
    private Boolean processed;

}
