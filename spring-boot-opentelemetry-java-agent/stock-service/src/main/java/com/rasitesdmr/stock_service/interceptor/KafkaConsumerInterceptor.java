package com.rasitesdmr.stock_service.interceptor;

import com.rasitesdmr.stock_service.domain.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.listener.RecordInterceptor;

import java.util.Objects;

@Slf4j
public class KafkaConsumerInterceptor<T> implements RecordInterceptor<String, T> {

    @Override
    public ConsumerRecord<String, T> intercept(ConsumerRecord<String, T> record, Consumer<String, T> consumer) {
        String kafkaLogId = getHeaderStringValueByKey(record, Constants.KAFKA_LOG_ID);
        log.info("[KafkaConsumerInterceptor] [KAFKA_LOG_ID: {}] topic='{}', partition={}, offset={}", kafkaLogId, record.topic(), record.partition(), record.offset());
        return record;
    }

    private String getHeaderStringValueByKey(ConsumerRecord<String, T> consumerRecord, String headerKey) {
        for (Header header : consumerRecord.headers()) {
            if (Objects.equals(header.key(), headerKey)) {
                return new String(header.value());
            }
        }
        return "";
    }
}