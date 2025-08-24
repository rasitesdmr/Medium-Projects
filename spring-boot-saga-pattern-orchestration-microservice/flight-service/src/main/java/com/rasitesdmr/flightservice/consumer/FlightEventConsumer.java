package com.rasitesdmr.flightservice.consumer;

import com.rasitesdmr.flightservice.service.FlightService;
import com.rasitesdmr.flightservice.topic.KafkaTopic;
import kafka.event.FlightStartEvent;
import kafka.event.HotelFlightReverseStartEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FlightEventConsumer {

    private final FlightService flightService;

    public FlightEventConsumer(FlightService flightService) {
        this.flightService = flightService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_FLIGHT_START, groupId = "orchestrator-service")
    public void consumerFlightStart(@Payload FlightStartEvent flightStartEvent,
                                    @Header(name = "saga_id", required = false) UUID sagaId,
                                    Acknowledgment ack) {
        flightService.createFlight(flightStartEvent, sagaId);
        ack.acknowledge();
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_FLIGHT_REVERSE_START,
            groupId = "orchestrator-service")
    public void consumerPaymentReverseStart(@Payload HotelFlightReverseStartEvent hotelFlightReverseStartEvent,
                                            @Header(name = "saga_id", required = false) UUID sagaId,
                                            Acknowledgment ack) {
        flightService.handleHotelFlightReverseStart(hotelFlightReverseStartEvent, sagaId);
        ack.acknowledge();
    }
}
