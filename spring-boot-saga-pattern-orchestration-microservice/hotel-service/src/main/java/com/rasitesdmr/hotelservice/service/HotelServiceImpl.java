package com.rasitesdmr.hotelservice.service;

import com.rasitesdmr.hotelservice.enums.HotelStatus;
import com.rasitesdmr.hotelservice.model.Hotel;
import com.rasitesdmr.hotelservice.producer.HotelEventProducer;
import com.rasitesdmr.hotelservice.repository.HotelRepository;
import kafka.event.HotelBookedEvent;
import kafka.event.HotelFailedEvent;
import kafka.event.HotelStartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public class HotelServiceImpl implements HotelService {

    private final static Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);
    private final HotelRepository hotelRepository;
    private final HotelEventProducer hotelEventProducer;

    public HotelServiceImpl(HotelRepository hotelRepository, HotelEventProducer hotelEventProducer) {
        this.hotelRepository = hotelRepository;
        this.hotelEventProducer = hotelEventProducer;
    }

    @Override
    public void saveHotel(Hotel hotel) {
        try {
            hotelRepository.save(hotel);
        } catch (Exception exception) {
            log.error("Hotel Save Error: {}", exception.getMessage());
        }
    }

    @Override
    public void createHotel(HotelStartEvent hotelStartEvent, UUID sagaId) {
        Hotel pendingHotel = buildHotelPendingObjectFromHotelStartEvent(hotelStartEvent, sagaId);
        saveHotel(pendingHotel);
        processBookingBasedOnHotelCode(pendingHotel);
    }

    private void processBookingBasedOnHotelCode(Hotel hotel) {
        final String hotelCode = hotel.getHotelCode();
        if (!hotelCode.equals("SAMSUN-OTEL")) {
            processHotelBooked(hotel);
        } else {
            processHotelFailed(hotel);
        }
    }

    private Hotel buildHotelPendingObjectFromHotelStartEvent(HotelStartEvent hotelStartEvent, UUID sagaId) {
        final UUID bookingId = hotelStartEvent.getBookingId();
        final String userId = hotelStartEvent.getUserId();
        final String hotelCode = hotelStartEvent.getHotelCode();
        final LocalDate checkIn = hotelStartEvent.getCheckIn();
        final LocalDate checkOut = hotelStartEvent.getCheckOut();
        final HotelStatus status = HotelStatus.PENDING;
        return Hotel.builder()
                .bookingId(bookingId)
                .sagaId(sagaId)
                .userId(userId)
                .hotelCode(hotelCode)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .status(status)
                .build();
    }

    private void processHotelBooked(Hotel hotel) {
        final UUID sagaId = hotel.getSagaId();
        final HotelStatus status = HotelStatus.BOOKED;
        hotel.setStatus(status);
        saveHotel(hotel);
        HotelBookedEvent hotelBookedEvent = buildHotelBookedEventObjectFromHotel(hotel);
        hotelEventProducer.producerHotelBooked(hotelBookedEvent, sagaId);
    }

    private void processHotelFailed(Hotel hotel) {
        final UUID sagaId = hotel.getSagaId();
        final HotelStatus status = HotelStatus.FAILED;
        hotel.setStatus(status);
        saveHotel(hotel);
        HotelFailedEvent hotelFailedEvent = buildHotelFailedEventObjectFromHotel(hotel);
        hotelEventProducer.producerHotelFailed(hotelFailedEvent, sagaId);
    }

    private HotelFailedEvent buildHotelFailedEventObjectFromHotel(Hotel hotel) {
        final UUID hotelId = hotel.getId();
        final UUID bookingId = hotel.getBookingId();
        final String userId = hotel.getUserId();
        final String hotelStatus = HotelStatus.FAILED.name();
        return HotelFailedEvent.builder()
                .hotelId(hotelId)
                .bookingId(bookingId)
                .userId(userId)
                .hotelStatus(hotelStatus)
                .build();
    }

    private HotelBookedEvent buildHotelBookedEventObjectFromHotel(Hotel hotel) {
        final UUID hotelId = hotel.getId();
        final UUID bookingId = hotel.getBookingId();
        final String userId = hotel.getUserId();
        final String hotelStatus = hotel.getStatus().name();
        return HotelBookedEvent.builder()
                .hotelId(hotelId)
                .bookingId(bookingId)
                .userId(userId)
                .hotelStatus(hotelStatus)
                .build();
    }
}
