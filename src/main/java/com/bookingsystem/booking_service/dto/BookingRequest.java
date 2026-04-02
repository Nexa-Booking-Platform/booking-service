package com.bookingsystem.booking_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    private String propertyId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
