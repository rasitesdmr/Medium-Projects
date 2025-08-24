package com.rasitesdmr.bookingservice.service;

import com.rasitesdmr.bookingservice.dto.request.CreateBookingRequest;
import com.rasitesdmr.bookingservice.dto.response.CreateBookingResponse;
import com.rasitesdmr.bookingservice.enums.BookingStatus;
import com.rasitesdmr.bookingservice.enums.StepState;
import com.rasitesdmr.bookingservice.exception.exceptions.InternalServerErrorException;
import kafka.event.*;
import com.rasitesdmr.bookingservice.producer.BookingEventProducer;
import com.rasitesdmr.bookingservice.model.Booking;
import com.rasitesdmr.bookingservice.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingEventProducer bookingEventProducer;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingEventProducer bookingEventProducer, BookingRepository bookingRepository) {
        this.bookingEventProducer = bookingEventProducer;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void saveBooking(Booking booking) {
        try {
            bookingRepository.save(booking);
        } catch (Exception exception) {
            log.error("Booking save failed : {}", exception.getMessage());
            throw new InternalServerErrorException("Booking save failed");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateBookingResponse createBooking(CreateBookingRequest createBookingRequest) {
        Booking booking = buildBookingObjectFromCreateBookingRequest(createBookingRequest);
        saveBooking(booking);
        BookingCreatedEvent event = buildBookingCreatedEventObjectFromBooking(booking);
        final UUID sagaId = booking.getSagaId();
        bookingEventProducer.producerBookingCreated(event, sagaId);
        return buildCreateBookingResponseObjectFromBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePaymentBookingCompletedStatus(PaymentBookingCompletedEvent paymentBookingCompletedEvent) {
        final UUID bookingId = paymentBookingCompletedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String paymentStatus = paymentBookingCompletedEvent.getPaymentStatus();
        booking.setPaymentState(StepState.valueOf(paymentStatus));
        saveBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFlightBookingConfirmedStatus(FlightBookingConfirmedEvent flightBookingConfirmedEvent) {
        final UUID bookingId = flightBookingConfirmedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String flightStatus = flightBookingConfirmedEvent.getFlightStatus();
        booking.setFlightState(StepState.valueOf(flightStatus));
        saveBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateHotelBookingBookedStatus(HotelBookingBookedEvent hotelBookingBookedEvent) {
        final UUID bookingId = hotelBookingBookedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String hotelStatus = hotelBookingBookedEvent.getHotelStatus();
        booking.setHotelState(StepState.valueOf(hotelStatus));
        saveBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBookingConfirmedStatus(BookingConfirmedEvent bookingConfirmedEvent) {
        final UUID bookingId = bookingConfirmedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String bookingStatus = bookingConfirmedEvent.getBookingStatus();
        booking.setStatus(BookingStatus.valueOf(bookingStatus));
        saveBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePaymentBookingFailedStatus(PaymentBookingFailedEvent paymentBookingFailedEvent) {
        final UUID bookingId = paymentBookingFailedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String paymentStatus = paymentBookingFailedEvent.getPaymentStatus();
        final String bookingStatus = paymentBookingFailedEvent.getBookingStatus();
        booking.setPaymentState(StepState.valueOf(paymentStatus));
        booking.setStatus(BookingStatus.valueOf(bookingStatus));
        saveBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFlightBookingFailedStatus(FlightBookingFailedEvent flightBookingFailedEvent) {
        final UUID bookingId = flightBookingFailedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String paymentStatus = flightBookingFailedEvent.getPaymentStatus();
        final String flightStatus = flightBookingFailedEvent.getFlightStatus();
        final String bookingStatus = flightBookingFailedEvent.getBookingStatus();
        booking.setFlightState(StepState.valueOf(flightStatus));
        booking.setPaymentState(StepState.valueOf(paymentStatus));
        booking.setStatus(BookingStatus.valueOf(bookingStatus));
        saveBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateHotelBookingFailedStatus(HotelBookingFailedEvent hotelBookingFailedEvent) {
        final UUID bookingId = hotelBookingFailedEvent.getBookingId();
        Booking booking = getLockByBookingId(bookingId);
        final String paymentStatus = StepState.REVERSED.name();
        final String flightStatus = StepState.REVERSED.name();
        final String hotelStatus = StepState.FAILED.name();
        final String bookingStatus = hotelBookingFailedEvent.getBookingStatus();
        booking.setHotelState(StepState.valueOf(hotelStatus));
        booking.setFlightState(StepState.valueOf(flightStatus));
        booking.setPaymentState(StepState.valueOf(paymentStatus));
        booking.setStatus(BookingStatus.valueOf(bookingStatus));
        saveBooking(booking);
    }

    @Override
    public Booking getLockByBookingId(UUID bookingId) {
        return bookingRepository.findLockById(bookingId);
    }

    private Booking buildBookingObjectFromCreateBookingRequest(CreateBookingRequest createBookingRequest) {
        final String userId = createBookingRequest.getUserId();
        final BigDecimal amountTotal = createBookingRequest.getAmountTotal();
        final String flightFrom = createBookingRequest.getFlightFrom();
        final String flightTo = createBookingRequest.getFlightTo();
        final LocalDate flightDate = createBookingRequest.getFlightDate();
        final String flightNo = createBookingRequest.getFlightNo();
        final String hotelCode = createBookingRequest.getHotelCode();
        final LocalDate checkIn = createBookingRequest.getCheckIn();
        final LocalDate checkOut = createBookingRequest.getCheckOut();
        final UUID sagaId = UUID.randomUUID();

        return Booking.builder()
                .userId(userId)
                .sagaId(sagaId)
                .status(BookingStatus.PENDING)
                .amountTotal(amountTotal)
                .flightFrom(flightFrom)
                .flightTo(flightTo)
                .flightDate(flightDate)
                .flightNo(flightNo)
                .hotelCode(hotelCode)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .paymentState(StepState.PENDING)
                .flightState(StepState.PENDING)
                .hotelState(StepState.PENDING)
                .build();
    }

    private BookingCreatedEvent buildBookingCreatedEventObjectFromBooking(Booking booking) {
        return BookingCreatedEvent.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .amountTotal(booking.getAmountTotal())
                .flightFrom(booking.getFlightFrom())
                .flightTo(booking.getFlightTo())
                .flightDate(booking.getFlightDate())
                .flightNo(booking.getFlightNo())
                .hotelCode(booking.getHotelCode())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .build();
    }

    private CreateBookingResponse buildCreateBookingResponseObjectFromBooking(Booking booking) {
        return CreateBookingResponse.builder()
                .bookingId(booking.getId())
                .sagaId(booking.getSagaId())
                .status(booking.getStatus())
                .paymentState(booking.getPaymentState())
                .flightState(booking.getFlightState())
                .hotelState(booking.getHotelState())
                .build();
    }
}
