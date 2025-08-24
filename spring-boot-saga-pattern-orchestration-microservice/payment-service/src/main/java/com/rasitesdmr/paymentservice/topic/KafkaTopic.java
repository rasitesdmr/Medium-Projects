package com.rasitesdmr.paymentservice.topic;

public final class KafkaTopic {
    public static final String TOPIC_PAYMENT_START= "payment-start";
    public static final String TOPIC_PAYMENT_COMPLETED= "payment-completed";
    public static final String TOPIC_PAYMENT_FAILED = "payment-failed";
    public static final String TOPIC_FLIGHT_PAYMENT_REVERSE_START = "flight-payment-reverse-start";
    public static final String TOPIC_FLIGHT_PAYMENT_REVERSED = "flight-payment-reversed";
    public static final String TOPIC_HOTEL_PAYMENT_REVERSE_START = "hotel-payment-reverse-start";
    public static final String TOPIC_HOTEL_PAYMENT_REVERSED = "hotel-payment-reversed";
}
