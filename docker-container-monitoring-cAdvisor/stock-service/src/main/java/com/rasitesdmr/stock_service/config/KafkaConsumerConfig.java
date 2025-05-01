package com.rasitesdmr.stock_service.config;

import com.rasitesdmr.stock_service.domain.constant.Constants;
import com.rasitesdmr.stock_service.interceptor.KafkaConsumerInterceptor;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.host}")
    private String kafkaHost;

    @Value(value = "${spring.kafka.port}")
    private String kafkaPort;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);


    public ConsumerFactory<String, Object> consumerFactory() {

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s",kafkaHost,kafkaPort));
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-topic");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>(Object.class);
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(CommonErrorHandler commonErrorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordInterceptor(new KafkaConsumerInterceptor<>());
        factory.setCommonErrorHandler(commonErrorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public CommonErrorHandler commonErrorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(kafkaTemplate, (cr, ex) -> {
                   logDeadLetterPublisher(cr);
                    int retryCount = getRetryCountFromHeader(cr);
                    int newRetryCount = retryCount + 1;
                    removeHeader(cr);
                    String newTopicName = determineQueueName(newRetryCount, cr);
                    return new TopicPartition(newTopicName, 0);
                }),
                 new FixedBackOff(Duration.ofMinutes(1).toMillis(), 0)
        );
    }


    private int getRetryCountFromHeader(ConsumerRecord<?, ?> consumerRecord) {
        for (Header header : consumerRecord.headers()) {
            if (Objects.equals(header.key(), Constants.ERROR_RETRY_COUNT)) {
                return Integer.parseInt(new String(header.value()));
            }
        }
        return 0;
    }

    private void removeHeader(ConsumerRecord<?, ?> cr) {
        cr.headers().remove(Constants.ERROR_RETRY_COUNT);
        cr.headers().remove(Constants.ERROR_CREATED_DATE);
        cr.headers().remove(Constants.KAFKA_ERROR_CLIENT_NAME);

        cr.headers().remove("kafka_dlt-original-topic");
        cr.headers().remove("kafka_dlt-original-partition");
        cr.headers().remove("kafka_dlt-original-offset");
        cr.headers().remove("kafka_dlt-original-timestamp-type");
        cr.headers().remove("kafka_dlt-original-timestamp");
        cr.headers().remove("kafka_dlt-original-consumer-group");
    }

    private String determineQueueName(int newRetryCount, ConsumerRecord<?, ?> cr) {
        String baseTopicName = cr.topic().replaceAll("(-error|-dlq)?-topic$", "");
        String newTopic;

        if (newRetryCount > Constants.ERROR_MAX_RETRY_COUNT) {
            newTopic = baseTopicName + "-dlq-topic";
        } else {
            newTopic = baseTopicName + "-error-topic";
            cr.headers().add(new RecordHeader(Constants.ERROR_RETRY_COUNT, String.valueOf(newRetryCount).getBytes()));
            cr.headers().add(new RecordHeader(Constants.KAFKA_ERROR_CLIENT_NAME, Constants.KAFKA_CLIENT_STOCK_NAME.getBytes()));
        }

        Date errorCreateDate = new Date();
        String convertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(errorCreateDate);
        cr.headers().add(new RecordHeader(Constants.ERROR_CREATED_DATE, convertDate.getBytes()));
        return newTopic;
    }

    private void logDeadLetterPublisher(ConsumerRecord<?, ?> cr){
        String kafkaLogId = getHeaderStringValueByKey(cr.headers(), Constants.KAFKA_LOG_ID);
        logger.error("[DeadLetterPublishingRecoverer] [KAFKA_LOG_ID: {}] topic='{}', partition={}, offset={}", kafkaLogId, cr.topic(), cr.partition(), cr.offset());
    }

    private String getHeaderStringValueByKey(Headers headers, String headerKey) {
        for (Header header : headers) {
            if (Objects.equals(header.key(), headerKey)) {
                return new String(header.value());
            }
        }
        return "";
    }
}

