package com.rasitesdmr.stock_service.interceptor;

import com.rasitesdmr.stock_service.domain.constant.Constants;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class KafkaProducerInterceptor implements ProducerInterceptor<String, Object> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerInterceptor.class);

    @Override
    public ProducerRecord<String, Object> onSend(ProducerRecord<String, Object> record) {
        setKafkaLogId(record);
        setKafkaClientName(record);
        setCreatedDate(record);
        logKafkaInterceptor(record);
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            logger.info("Message successfully sent to topic={} partition={} offset={}", metadata.topic(), metadata.partition(), metadata.offset());
        } else {
            logger.error("Failed to send Kafka message", exception);
        }
    }

    @Override
    public void close() {
        logger.info("KafkaProducerInterceptor closed.");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    private void setKafkaLogId(ProducerRecord<String, Object> record) {
        if (record.headers().lastHeader(Constants.KAFKA_LOG_ID) == null) {
            String kafkaLogId = UUID.randomUUID().toString();
            record.headers().add(Constants.KAFKA_LOG_ID, kafkaLogId.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void setKafkaClientName(ProducerRecord<String, Object> record) {
        if (record.headers().lastHeader(Constants.KAFKA_BASE_CLIENT_NAME) == null) {
            record.headers().add(Constants.KAFKA_BASE_CLIENT_NAME, Constants.KAFKA_CLIENT_STOCK_NAME.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void setCreatedDate(ProducerRecord<String, Object> record) {
        if (record.headers().lastHeader(Constants.CREATED_DATE) == null) {
            Date date = new Date();
            String convertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date);
            record.headers().add(Constants.CREATED_DATE, convertDate.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void logKafkaInterceptor(ProducerRecord<String, Object> record){
        String kafkaLogId = getHeaderStringValueByKey(record, Constants.KAFKA_LOG_ID);
        logger.info("[KafkaProducerInterceptor] [KAFKA_LOG_ID: {}] topic='{}', partition={}", kafkaLogId, record.topic(), record.partition());
    }

    private String getHeaderStringValueByKey(ProducerRecord<String, Object> record, String headerKey) {
        for (Header header : record.headers()) {
            if (Objects.equals(header.key(), headerKey)) {
                return new String(header.value());
            }
        }
        return "";
    }
}
