package com.bookingsystem.booking_service.controller;

import com.bookingsystem.booking_service.dto.BookingRequest;
import com.bookingsystem.booking_service.dto.BookingResponse;
import com.bookingsystem.booking_service.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody BookingRequest request) {

        return bookingService.createBooking(userId, request);
    }
}