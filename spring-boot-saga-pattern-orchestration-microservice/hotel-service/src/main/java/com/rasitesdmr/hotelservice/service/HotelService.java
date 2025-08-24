package com.rasitesdmr.hotelservice.service;

import com.rasitesdmr.hotelservice.model.Hotel;
import kafka.event.HotelStartEvent;

import java.util.UUID;

public interface HotelService {

    void saveHotel(Hotel hotel);
    void createHotel(HotelStartEvent hotelStartEvent, UUID sagaId);
}
