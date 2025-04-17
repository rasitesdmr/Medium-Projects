package com.rasitesdmr.order_service.domain.constant;

import java.util.Date;

public class Constants {
    public static final String  CREATED_DATE = "CREATED_DATE";
    public static final String  ERROR_CREATED_DATE = "ERROR_CREATED_DATE";

    public static final String KAFKA_BASE_CLIENT_NAME = "KAFKA_BASE_CLIENT_NAME";
    public static final String KAFKA_ERROR_CLIENT_NAME = "KAFKA_ERROR_CLIENT_NAME";

    public static final String KAFKA_CLIENT_ORDER_NAME = "order-service";
    public static final String KAFKA_CLIENT_STOCK_NAME = "stock-service";

    public static final String KAFKA_LOG_ID = "KAFKA_LOG_ID";
    public static final String ERROR_RETRY_COUNT = "ERROR_RETRY_COUNT";
    public static final int ERROR_MAX_RETRY_COUNT = 5;
}
