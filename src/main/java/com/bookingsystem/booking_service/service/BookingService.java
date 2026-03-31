package com.bookingsystem.booking_service.service;

import com.bookingsystem.booking_service.dto.BookingRequest;
import com.bookingsystem.booking_service.entity.Booking;

public interface BookingService {
    Booking createBooking(String userId, BookingRequest request);
}
