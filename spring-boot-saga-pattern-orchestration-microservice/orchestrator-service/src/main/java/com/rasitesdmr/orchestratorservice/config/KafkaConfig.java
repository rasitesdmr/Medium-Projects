package com.rasitesdmr.orchestratorservice.config;

import com.rasitesdmr.orchestratorservice.topic.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic bookingCreatedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_BOOKING_CREATED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bookingConfirmedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_BOOKING_CONFIRMED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bookingPaymentCompletedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_PAYMENT_BOOKING_COMPLETED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bookingFlightConfirmedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_BOOKING_CONFIRMED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bookingHotelBookedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_HOTEL_BOOKING_BOOKED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bookingPaymentFailedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_PAYMENT_BOOKING_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentStartTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_PAYMENT_START)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentCompletedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_PAYMENT_COMPLETED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_PAYMENT_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic flightStartTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_START)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic flightConfirmedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_CONFIRMED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic flightFailedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic hotelStartTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_HOTEL_START)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic hotelBookedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_HOTEL_BOOKED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic hotelFailedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_HOTEL_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentReverseStartTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_PAYMENT_REVERSE_START)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic bookingPaymentReversedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_BOOKING_FAILED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentReversedTopic() {
        return TopicBuilder.name(KafkaTopic.TOPIC_FLIGHT_PAYMENT_REVERSED)
                .partitions(1)
                .replicas(1)
                .build();
    }
}