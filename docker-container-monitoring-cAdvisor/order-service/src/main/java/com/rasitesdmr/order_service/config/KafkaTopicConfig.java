package com.rasitesdmr.order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ORDER_CREATED_TOPIC = "order-created-topic";
    public static final String ORDER_CREATED_ERROR_TOPIC = "order-created-error-topic";
    public static final String ORDER_CREATED_DLQ_TOPIC = "order-created-dlq-topic";

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(ORDER_CREATED_TOPIC)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic orderCreatedErrorTopic() {
        return TopicBuilder.name(ORDER_CREATED_ERROR_TOPIC)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic orderCreatedDLQTopic() {
        return TopicBuilder.name(ORDER_CREATED_DLQ_TOPIC)
                .partitions(1)
                .build();
    }
}
