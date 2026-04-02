package com.bookingsystem.booking_service.service;

import com.bookingsystem.booking_service.dto.BookingRequest;
import com.bookingsystem.booking_service.dto.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(String userId, BookingRequest request);
}
