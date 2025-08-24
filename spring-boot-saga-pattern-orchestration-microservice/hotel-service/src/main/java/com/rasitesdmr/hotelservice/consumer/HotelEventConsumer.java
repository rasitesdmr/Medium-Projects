package com.rasitesdmr.hotelservice.consumer;

import com.rasitesdmr.hotelservice.service.HotelService;
import com.rasitesdmr.hotelservice.topic.KafkaTopic;
import kafka.event.HotelStartEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HotelEventConsumer {

    private final HotelService hotelService;

    public HotelEventConsumer(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @KafkaListener(topics = KafkaTopic.TOPIC_HOTEL_START, groupId = "orchestrator-service")
    public void consumerHotelStart(@Payload HotelStartEvent hotelStartEvent,
                                   @Header(name = "saga_id", required = false) UUID sagaId,
                                   Acknowledgment ack) {
        hotelService.createHotel(hotelStartEvent, sagaId);
        ack.acknowledge();
    }
}
