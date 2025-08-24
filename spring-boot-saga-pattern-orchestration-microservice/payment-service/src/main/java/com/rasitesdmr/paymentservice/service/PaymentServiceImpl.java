package com.rasitesdmr.paymentservice.service;

import com.rasitesdmr.paymentservice.enums.PaymentStatus;
import com.rasitesdmr.paymentservice.model.Payment;
import com.rasitesdmr.paymentservice.producer.PaymentEventProducer;
import com.rasitesdmr.paymentservice.repository.PaymentRepository;
import kafka.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final static Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentEventProducer paymentEventProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    @Override
    public void savePayment(Payment payment) {
        try {
            paymentRepository.save(payment);
        } catch (Exception exception) {
            log.error("Payment save failed : {}", exception.getMessage());
        }
    }

    @Override
    public Payment getPaymentByBookingId(UUID bookingId) {
        return paymentRepository.findPaymentByBookingId(bookingId);
    }

    @Override
    public void createPayment(PaymentStartEvent paymentStartEvent, UUID sagaId) {
        Payment pendingPayment = buildPaymentPendingObjectFromPaymentStartEvent(paymentStartEvent, sagaId);
        savePayment(pendingPayment);
        processBookingTotalAmountAgainstBalance(pendingPayment);
    }

    @Override
    public void handleFlightPaymentReverseStart(FlightPaymentReverseStartEvent flightPaymentReverseStartEvent, UUID sagaId) {
        final UUID bookingId = flightPaymentReverseStartEvent.getBookingId();
        Payment payment = getPaymentByBookingId(bookingId);
        updateFlightPaymentReverseByPaymentStatus(payment, flightPaymentReverseStartEvent);
        FlightPaymentReversedEvent flightPaymentReversedEvent = buildFlightPaymentReversedEventObjectFromPaymentAndEvent(payment, flightPaymentReverseStartEvent);
        paymentEventProducer.producerFlightPaymentReversed(flightPaymentReversedEvent, sagaId);
    }

    @Override
    public void handleHotelPaymentReverseStart(HotelPaymentReverseStartEvent hotelPaymentReverseStartEvent, UUID sagaId) {
        final UUID bookingId = hotelPaymentReverseStartEvent.getBookingId();
        Payment payment = getPaymentByBookingId(bookingId);
        updateHotelPaymentReverseByPaymentStatus(payment,hotelPaymentReverseStartEvent);
        HotelPaymentReversedEvent hotelPaymentReversedEvent = buildHotelPaymentReversedEventObjectFromPayment(payment);
        paymentEventProducer.producerHotelPaymentReversed(hotelPaymentReversedEvent, sagaId);
    }

    private HotelPaymentReversedEvent buildHotelPaymentReversedEventObjectFromPayment(Payment payment){
        final UUID paymentId = payment.getId();
        final UUID bookingId = payment.getBookingId();
        final String userId = payment.getUserId();
        final String paymentStatus = payment.getStatus().name();
        return HotelPaymentReversedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .build();
    }

    private FlightPaymentReversedEvent buildFlightPaymentReversedEventObjectFromPaymentAndEvent(Payment payment, FlightPaymentReverseStartEvent flightPaymentReverseStartEvent) {
        final UUID paymentId = payment.getId();
        final UUID bookingId = payment.getBookingId();
        final String userId = payment.getUserId();
        final String paymentStatus = payment.getStatus().name();
        final String flightStatus = flightPaymentReverseStartEvent.getFlightStatus();
        final String bookingStatus = flightPaymentReverseStartEvent.getBookingStatus();
        return FlightPaymentReversedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .flightStatus(flightStatus)
                .bookingStatus(bookingStatus)
                .build();
    }

    private void updateHotelPaymentReverseByPaymentStatus(Payment payment, HotelPaymentReverseStartEvent hotelPaymentReverseStartEvent){
        final String paymentStatus = hotelPaymentReverseStartEvent.getPaymentStatus();
        payment.setStatus(PaymentStatus.valueOf(paymentStatus));
        savePayment(payment);
    }

    private void updateFlightPaymentReverseByPaymentStatus(Payment payment, FlightPaymentReverseStartEvent flightPaymentReverseStartEvent) {
        final String paymentStatus = flightPaymentReverseStartEvent.getPaymentStatus();
        payment.setStatus(PaymentStatus.valueOf(paymentStatus));
        savePayment(payment);
    }

    private Payment buildPaymentPendingObjectFromPaymentStartEvent(PaymentStartEvent paymentStartEvent, UUID sagaId) {
        final UUID bookingId = paymentStartEvent.getBookingId();
        final String userId = paymentStartEvent.getUserId();
        final BigDecimal amountTotal = paymentStartEvent.getAmountTotal();
        final PaymentStatus status = PaymentStatus.PENDING;
        return Payment.builder()
                .bookingId(bookingId)
                .userId(userId)
                .sagaId(sagaId)
                .amountTotal(amountTotal)
                .status(status)
                .build();
    }

    private void processBookingTotalAmountAgainstBalance(Payment payment) {
        boolean isTotalAmountValidation = isBalanceEnoughForBooking(payment.getAmountTotal());
        if (isTotalAmountValidation) {
            processPaymentCompleted(payment);
        } else {
            processPaymentFailed(payment);
        }
    }

    private boolean isBalanceEnoughForBooking(BigDecimal amountTotal) {
        BigDecimal userTotalBalance = new BigDecimal(400000);
        return amountTotal.compareTo(userTotalBalance) <= 0;
    }

    private void processPaymentCompleted(Payment payment) {
        final UUID sagaId = payment.getSagaId();
        final PaymentStatus status = PaymentStatus.COMPLETED;
        payment.setStatus(status);
        savePayment(payment);
        PaymentCompletedEvent paymentCompletedEvent = buildPaymentCompletedEventObjectFromPayment(payment);
        paymentEventProducer.producerPaymentCompleted(paymentCompletedEvent, sagaId);
    }

    private void processPaymentFailed(Payment payment) {
        final UUID sagaId = payment.getSagaId();
        final PaymentStatus status = PaymentStatus.FAILED;
        payment.setStatus(status);
        savePayment(payment);
        PaymentFailedEvent paymentFailedEvent = buildPaymentFailedEventObjectFromPayment(payment);
        paymentEventProducer.producerPaymentFailed(paymentFailedEvent, sagaId);
    }

    private PaymentFailedEvent buildPaymentFailedEventObjectFromPayment(Payment payment) {
        final UUID paymentId = payment.getId();
        final UUID bookingId = payment.getBookingId();
        final String userId = payment.getUserId();
        final String paymentStatus = payment.getStatus().name();
        return PaymentFailedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .build();
    }

    private PaymentCompletedEvent buildPaymentCompletedEventObjectFromPayment(Payment payment) {
        final UUID paymentId = payment.getId();
        final UUID bookingId = payment.getBookingId();
        final String userId = payment.getUserId();
        final String paymentStatus = payment.getStatus().name();
        return PaymentCompletedEvent.builder()
                .paymentId(paymentId)
                .bookingId(bookingId)
                .userId(userId)
                .paymentStatus(paymentStatus)
                .build();
    }


}
